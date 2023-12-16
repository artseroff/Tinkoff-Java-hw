package edu.hw8.task2;

import java.util.concurrent.CountDownLatch;
import org.jetbrains.annotations.NotNull;

public class FibonacciRunnable implements Runnable {
    private long result = 1;
    private final int n;
    private final CountDownLatch latch;

    public FibonacciRunnable(int n, @NotNull CountDownLatch latch) {
        if (n < 0) {
            throw new IllegalArgumentException("n не должно быть отрицательным");
        }
        this.n = n;
        this.latch = latch;
    }

    @Override
    public void run() {
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        latch.countDown();
    }

    public long getResult() {
        return result;
    }
}
