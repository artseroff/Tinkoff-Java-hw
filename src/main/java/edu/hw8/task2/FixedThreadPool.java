package edu.hw8.task2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.jetbrains.annotations.NotNull;

public class FixedThreadPool implements ThreadPool {
    private final Thread[] threads;
    private final BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();
    private volatile boolean isShutdown = false;

    private FixedThreadPool(int threadsCount) {
        threads = new Thread[threadsCount];
        for (int i = 0; i < threadsCount; i++) {
            threads[i] = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted() && !isShutdown) {
                    try {
                        tasks.take().run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }
    }

    public static FixedThreadPool create(int threadsCount) {
        if (threadsCount < 1) {
            throw new IllegalArgumentException("Количество потоков должно быть > 0");
        }
        return new FixedThreadPool(threadsCount);
    }

    @Override
    public void start() {
        isShutdown = false;
        for (Thread thread : threads) {
            thread.start();
        }
    }

    @Override
    public void execute(@NotNull Runnable runnable) {
        if (isShutdown) {
            throw new IllegalStateException("Работа пула потоков уже завершилась");
        }
        try {
            tasks.put(runnable);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void close() {
        isShutdown = true;
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }
}
