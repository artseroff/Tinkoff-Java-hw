package edu.hw7;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Task4Test {
    private final static Logger LOGGER = LogManager.getLogger();

    @ParameterizedTest
    @MethodSource("params")
    void calculatePi(long n, double delta) {
        double expected = Math.PI;
        double actual = Task4.calculatePi(n);
        Assertions.assertEquals(expected, actual, delta);
    }

    @ParameterizedTest
    @MethodSource("params")
    void calculatePiMultithreadedDefaultTest(long n, double delta) throws InterruptedException {
        double expected = Math.PI;
        double actual = Task4.calculatePiMultithreaded(n);
        Assertions.assertEquals(expected, actual, delta);
    }

    @ParameterizedTest
    @MethodSource("params")
    void performanceTest(long n, double delta) throws InterruptedException {
        StringBuilder performance = new StringBuilder();
        performance.append("\nn = %,d\n".formatted(n));
        double expected = Math.PI;

        long time = System.nanoTime();
        double actual = Task4.calculatePi(n);
        time = System.nanoTime() - time;
        double time1 = time;
        Assertions.assertEquals(expected, actual, delta);
        performance.append("Single thread:\n")
            .append(getResult(time, expected, actual));

        long timeSum = 0;
        int[] threadsCount = new int[] {2, 4, 8, 16, 32};
        for (int t : threadsCount) {
            time = System.nanoTime();
            actual = Task4.calculatePiMultithreaded(n, t);
            time = System.nanoTime() - time;
            timeSum += time;
            Assertions.assertEquals(expected, actual, delta);
            performance.append("\nMultithread (%d threads):\n".formatted(t))
                .append(getResult(time, expected, actual));
        }
        performance.append("\nAverage execution time boost: %f\n"
            .formatted(time1 / (timeSum / (double) threadsCount.length)));
        LOGGER.info(performance.toString());
    }

    @SuppressWarnings("MagicNumber")
    private static Arguments[] params() {
        return new Arguments[] {
            Arguments.of(10_000_000, 0.005),
            Arguments.of(100_000_000, 0.001),
            Arguments.of(1_000_000_000, 0.0001)
        };
    }

    private double absoluteError(double expected, double actual) {
        return Math.abs(expected - actual);
    }

    private double relativeError(double expected, double actual) {
        return absoluteError(expected, actual) / expected;
    }

    private String getResult(long time, double expected, double actual) {
        return """
            Exec time:          %d sec %d milli sec %d micro sec %d nano sec
            Absolute error:     %.15f
            Relative error:     %.15f
            """.formatted(time / 1_000_000_000, time / 1_000_000 % 1_000, time / 1_000 % 1_000, time % 1_000,
            absoluteError(expected, actual), relativeError(expected, actual)
        );
    }

    @Test
    void calculatePiExceptionTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Task4.calculatePi(0));
    }

    @Test
    void calculatePiMultithreadedExceptionTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Task4.calculatePiMultithreaded(8, 0));
    }
}
