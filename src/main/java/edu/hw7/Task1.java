package edu.hw7;

import java.util.concurrent.atomic.AtomicInteger;

public class Task1 {

    private Task1() {
    }

    public static int incrementCounter(int countThreads, int countIterationsPerThread) throws InterruptedException {
        if (countThreads < 1) {
            throw new IllegalArgumentException("Threads count must be more than 0");
        }
        if (countIterationsPerThread < 1) {
            throw new IllegalArgumentException("Count iterations must be more than 0");
        }
        AtomicInteger counter = new AtomicInteger();
        Thread[] threads = new Thread[countThreads];
        for (int i = 0; i < countThreads; i++) {
            threads[i] = new Thread(getCountingOperation(counter, countIterationsPerThread));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        return counter.get();
    }

    private static Runnable getCountingOperation(AtomicInteger counter, int countIterations) {
        return () -> {
            for (int i = 0; i < countIterations; i++) {
                counter.incrementAndGet();
            }
        };
    }
}
