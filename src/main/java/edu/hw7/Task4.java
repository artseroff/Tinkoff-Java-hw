package edu.hw7;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Task4 {
    private static final int MACHINE_THREAD_COUNT = Runtime.getRuntime().availableProcessors();

    private Task4() {
    }

    @SuppressWarnings("MagicNumber")
    public static double calculatePi(long iterationsCount) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        checkIsPositiveOrThrow(iterationsCount);
        long circleCount = 0;
        long totalCount = 0;
        for (long i = 0; i < iterationsCount; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();
            if (Math.pow(x, 2) + Math.pow(y, 2) <= 1) {
                circleCount++;
            }
            totalCount++;
        }
        return 4 * circleCount / (double) totalCount;
    }

    public static double calculatePiMultithreaded(long iterationsCount) throws InterruptedException {
        return calculatePiMultithreaded(iterationsCount, MACHINE_THREAD_COUNT);
    }

    public static double calculatePiMultithreaded(long iterationsCount, int threadsCount) throws InterruptedException {
        checkIsPositiveOrThrow(iterationsCount);
        checkIsPositiveOrThrow(threadsCount);
        Thread[] threads = new Thread[threadsCount];
        double[] answer = new double[threadsCount];
        for (int i = 0; i < threadsCount; i++) {
            final int finalI = i;
            final long threadIterationsCount =
                iterationsCount / threadsCount + (i == threadsCount - 1 ? iterationsCount % threadsCount : 0);
            threads[i] = new Thread(() -> answer[finalI] = calculatePi(threadIterationsCount));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        return Arrays.stream(answer).sum() / (double) threadsCount;
    }

    private static void checkIsPositiveOrThrow(long n) {
        if (n < 1) {
            throw new IllegalArgumentException("number must be more than 0");
        }
    }
}
