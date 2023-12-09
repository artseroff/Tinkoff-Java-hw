package edu.hw9.task1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.jetbrains.annotations.NotNull;

public class StatsCollector {
    private final static int COUNT_THREADS = Runtime.getRuntime().availableProcessors();
    private final ExecutorService executorService;
    private final List<Future<Metric>> futureMetrics = Collections.synchronizedList(new LinkedList<>());

    public StatsCollector() {
        executorService = Executors.newFixedThreadPool(COUNT_THREADS);
    }

    public void push(@NotNull String metricName, double[] values) {
        var future = executorService.submit(new StatsCalculator(metricName, values));
        futureMetrics.add(future);
    }

    public List<Metric> stats() throws ExecutionException, InterruptedException {
        List<Metric> results = new ArrayList<>();
        for (Future<Metric> futureMetric : futureMetrics) {
            results.add(futureMetric.get());
        }
        return results;
    }

}
