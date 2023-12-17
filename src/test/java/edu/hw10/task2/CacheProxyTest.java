package edu.hw10.task2;

import edu.hw10.task1.entity.POJOPerson;
import edu.hw10.task2.storage.DiskStorage;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.UncheckedIOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CacheProxyTest {
    @TempDir
    private Path tempDir;

    private static final String CACHE_FILENAME = "1.txt";

    interface SimpleCalculator {
        int summa(int a, int b);

        int getCountInvokes();
    }

    interface DiskCachedCalculator extends SimpleCalculator {
        @Cache(persist = true)
        int summa(int a, int b);

    }

    interface MemoryCachedCalculator extends SimpleCalculator {
        @Cache()
        int summa(int a, int b);

    }

    public static class DiskCachedCalculatorImpl implements DiskCachedCalculator {
        private int countInvokes = 0;

        public DiskCachedCalculatorImpl() {

        }

        @Override
        public int summa(int a, int b) {
            countInvokes++;
            return a + b;
        }

        @Override
        public int getCountInvokes() {
            return countInvokes;
        }
    }

    public static class MemoryCachedCalculatorImpl implements MemoryCachedCalculator {
        private int countInvokes = 0;

        @Override
        public int summa(int a, int b) {
            countInvokes++;
            return a + b;
        }

        @Override
        public int getCountInvokes() {
            return countInvokes;
        }
    }

    public static class SimpleCalculatorImpl implements SimpleCalculator {
        private int countInvokes = 0;

        @Override
        public int summa(int a, int b) {
            countInvokes++;
            return a + b;
        }

        @Override
        public int getCountInvokes() {
            return countInvokes;
        }
    }

    private static Arguments[] params() {
        return new Arguments[] {
            Arguments.of((Supplier<SimpleCalculator>) SimpleCalculatorImpl::new, 5),
            Arguments.of((Supplier<SimpleCalculator>) DiskCachedCalculatorImpl::new, 2),
            Arguments.of((Supplier<SimpleCalculator>) MemoryCachedCalculatorImpl::new, 2)
        };
    }

    @ParameterizedTest
    @MethodSource("params")
    void cachedTest(Supplier<SimpleCalculator> constructor, int expectedCountInvokes) throws IOException {
        //Arrange
        SimpleCalculator calculator = constructor.get();
        var path = tempDir.resolve(CACHE_FILENAME);
        SimpleCalculator proxy = (SimpleCalculator) CacheProxy.build(calculator, path);
        int firstExampleExpected = 3;
        int secondExampleExpected = 10;

        //Act
        int firstExampleActual;
        firstExampleActual = proxy.summa(1, 2);
        firstExampleActual = proxy.summa(1, 2);
        firstExampleActual = proxy.summa(1, 2);

        int secondExampleActual;
        secondExampleActual = proxy.summa(1, 9);
        secondExampleActual = proxy.summa(1, 9);

        //Assert
        Assertions.assertEquals(firstExampleExpected, firstExampleActual);
        Assertions.assertEquals(secondExampleExpected, secondExampleActual);
        Assertions.assertEquals(expectedCountInvokes, proxy.getCountInvokes());

    }

    @Test
    void diskCachedTestFileContent() throws IOException, ClassNotFoundException {
        //Arrange
        DiskCachedCalculatorImpl calculator = new DiskCachedCalculatorImpl();
        var path = tempDir.resolve(CACHE_FILENAME);
        DiskCachedCalculator proxy = (DiskCachedCalculator) CacheProxy.build(calculator, path);

        //Act
        proxy.summa(1, 2);
        proxy.summa(1, 2);

        DiskStorage storage = DiskStorage.load(path);
        Set<Map.Entry<String, Object>> entries = storage.getCache().entrySet();
        var entry = entries
            .stream()
            .toList()
            .get(0);

        //Assert
        Assertions.assertEquals(1, calculator.getCountInvokes());
        Assertions.assertEquals(1, entries.size());
        Assertions.assertEquals("summa(1; 2)", entry.getKey());
        Assertions.assertEquals(3, entry.getValue());
    }

    @Test
    void saveNotSerializableObjectTest() throws NoSuchFileException {
        //Arrange
        Throwable expected = new UncheckedIOException(new NotSerializableException());

        //Act
        var diskStorage = DiskStorage.buildStorageAssociatedWithFile(tempDir.resolve(CACHE_FILENAME));
        Throwable t = org.assertj.core.api.Assertions
            .catchThrowable(() -> diskStorage.put("", new POJOPerson(1, "", "", "")));

        //Assert
        Assertions.assertEquals(expected.getClass(), t.getClass());
        Assertions.assertEquals(expected.getCause().getClass(), t.getCause().getClass());
    }
}
