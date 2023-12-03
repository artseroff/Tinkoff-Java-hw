package edu.hw8.task3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Map;
import java.util.stream.Stream;

class HackTest {
    private final static Logger LOGGER = LogManager.getLogger();
    private static final Map<String, String> passwords = Map.of(
        "c4ca4238a0b923820dcc509a6f75849b", "a.v.petrov",
        "900150983cd24fb0d6963f7d28e17f72", "i.b.antonov",
        "827ccb0eea8a706c4c34a16891f84e7b", "k.g.sharikov",
        "dcd91c643b2ad804cd5d7851019f38e9", "a.a.almazov"
    );
    private static final Map<String, String> expected = Map.of(
        "a.v.petrov", "1",
        "i.b.antonov", "abc",
        "k.g.sharikov", "12345",
        "a.a.almazov", "abac1"
    );

    @ParameterizedTest
    @MethodSource("hackers")
    void hackTest(AbstractPasswordHacker cracker) {
        Map<String, String> actual = cracker.hack();
        Assertions.assertEquals(expected, actual);
    }

    private static Stream<Arguments> hackers() {
        return Stream.of(
            Arguments.of(new SimplePasswordHacker(passwords)),
            Arguments.of(new ConcurrentPasswordHacker(passwords))
        );
    }

    @Test
    void performanceTest() {
        StringBuilder performance = new StringBuilder();

        long time = System.nanoTime();
        Map<String, String> actual = new SimplePasswordHacker(passwords).hack();
        time = System.nanoTime() - time;
        double time1 = time;
        Assertions.assertEquals(expected, actual);
        performance.append("\nОднопоточная реализация:\n").append(timeToString(time));

        long timeSum = 0;
        int[] threadsCount = new int[] { 2, 4, 8 };
        for (int t : threadsCount) {
            time = System.nanoTime();
            actual = new ConcurrentPasswordHacker(passwords, t).hack();
            time = System.nanoTime() - time;
            timeSum += time;
            Assertions.assertEquals(expected, actual);
            performance.append("\nМногопоточная реализация (кол-во потоков %d):\n".formatted(t)).append(timeToString(time));
        }

        performance.append("\nСреднее ускорение решения: %f\n"
            .formatted(time1 / (timeSum / (double) threadsCount.length)));
        LOGGER.info(performance.toString());
    }

    private String timeToString(long time) {
        return "Время работы: %d с %d мс %d мкс %d нс\n"
            .formatted(time / 1_000_000_000, time / 1_000_000 % 1_000, time / 1_000 % 1_000, time % 1_000);
    }
}
