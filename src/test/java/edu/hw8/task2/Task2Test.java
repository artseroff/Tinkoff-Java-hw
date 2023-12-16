package edu.hw8.task2;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FixedThreadPoolTest {
    private final Map<Integer, Long> fibonacci = Map.of(
        0, 1L,
        1, 1L,
        2, 2L,
        3, 6L,
        4, 24L,
        10, 3628800L,
        12, 479001600L,
        16, 20922789888000L,
        20, 2432902008176640000L
    );

    @Test
    void threadPoolTest() throws Exception {
        Map<Integer, FibonacciRunnable> tasks = new HashMap<>();
        CountDownLatch latch = new CountDownLatch(fibonacci.keySet().size());
        try (ThreadPool pool = FixedThreadPool.create(Runtime.getRuntime().availableProcessors())) {
            pool.start();
            for (int n : fibonacci.keySet()) {
                FibonacciRunnable task = new FibonacciRunnable(n, latch);
                tasks.put(n, task);
                pool.execute(task);
            }
            try {
                latch.await();
            } catch (InterruptedException ignored) {
            }
        }
        for (int n : fibonacci.keySet()) {
            Assertions.assertEquals(fibonacci.get(n), tasks.get(n).getResult());
        }
    }

    @Test
    void threadPoolCreationExceptionTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> FixedThreadPool.create(0));
    }
}
