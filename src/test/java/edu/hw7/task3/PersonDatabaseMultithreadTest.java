package edu.hw7.task3;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PersonDatabaseMultithreadTest {

    @SuppressWarnings("MagicNumber")
    private static Arguments[] validParams() {
        return new Arguments[] {
            Arguments.of((Supplier<PersonDatabase>) SyncPersonDatabase::new, 500),
            Arguments.of((Supplier<PersonDatabase>) LockPersonDatabase::new, 500)
        };
    }

    @ParameterizedTest
    @MethodSource("validParams")
    void tryFindTestRepeatedWrapper(Supplier<PersonDatabase> constructor, int reps) throws InterruptedException {
        for (int i = 0; i < reps; i++) {
            tryFindTest(constructor.get());
        }
    }

    void tryFindTest(PersonDatabase db) throws InterruptedException {

        int maxId = 10;

        int countAddThreads = 8;
        int countFindThreads = 8;

        AtomicBoolean isAllAttrFind = new AtomicBoolean(false);
        AtomicBoolean isPersonFind = new AtomicBoolean(false);

        List<Thread> threads = new LinkedList<>();

        Runnable runnableAdd = () -> {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            while (!isPersonFind.get()) {
                int randId = random.nextInt(maxId + 1);
                String stringAttribute = String.valueOf(randId);
                try {
                    db.add(new Person(
                        randId,
                        stringAttribute,
                        stringAttribute,
                        stringAttribute
                    ));
                } catch (NonuniqueIDException ignored) {
                }
            }
        };

        for (int i = 0; i < countAddThreads; i++) {
            threads.add(new Thread(runnableAdd));
        }

        Runnable runnableFind = () -> {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            while (!isPersonFind.get()) {

                String stringAttribute = String.valueOf(random.nextInt(maxId + 1));
                var list = db.findByName(stringAttribute);
                if (list != null) {
                    isPersonFind.set(true);
                    isAllAttrFind.set(true);
                    list = db.findByAddress(stringAttribute);
                    isAllAttrFind.set(isAllAttrFind.get() && list != null);
                    list = db.findByPhone(stringAttribute);
                    isAllAttrFind.set(isAllAttrFind.get() && list != null);
                }
            }
        };

        for (int i = 0; i < countFindThreads; i++) {
            threads.add(new Thread(runnableFind));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
        Assertions.assertTrue(isPersonFind.get());
        Assertions.assertTrue(isAllAttrFind.get());
    }
}
