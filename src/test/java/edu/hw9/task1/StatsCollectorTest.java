package edu.hw9.task1;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StatsCollectorTest {

    private static final double COMPARING_DELTA = 1e-5;

    @Test
    void statsTest() throws InterruptedException, ExecutionException {
        //Arrange
        List<Metric> expectedMetrics = List.of(
            new Metric("name1", 10.0, 2.5, 4, 1),
            new Metric("name2", 1, 0.33333, 0.33333, 0.33333),
            new Metric("name3", 0, 0, 0, 0)
        );

        Map<String, double[]> statsMap = Map.of(
            "name1", new double[] {1.0, 2.0, 3.0, 4.0},
            "name2", new double[] {0.33333, 0.33333, 0.33333},
            "name3", new double[] {0.0}
        );

        StatsCollector collector = new StatsCollector();

        Thread[] producers = new Thread[statsMap.size()];

        //Act
        int i = 0;
        for (var entry : statsMap.entrySet()) {
            producers[i] = new Thread(() -> collector.push(entry.getKey(), entry.getValue()));
            producers[i].start();
            i++;
        }

        for (var producer : producers) {
            producer.join();
        }

        List<Metric> result = collector.stats();

        //Assert
        for (var metric : result) {
            var expected = expectedMetrics.stream()
                .filter(e -> e.name().equals(metric.name()))
                .findFirst()
                .orElse(null);

            Assertions.assertEquals(expected.name(), metric.name());
            Assertions.assertEquals(expected.sum(), metric.sum(), COMPARING_DELTA);
            Assertions.assertEquals(expected.average(), metric.average(), COMPARING_DELTA);
            Assertions.assertEquals(expected.min(), metric.min(), COMPARING_DELTA);
            Assertions.assertEquals(expected.max(), metric.max(), COMPARING_DELTA);
        }
    }

    @Test
    void complexTest() throws InterruptedException, ExecutionException {
        //Arrange
        Metric expected = new Metric("name1", 10.0, 2.5, 4, 1);

        AbstractMap.SimpleEntry<String, double[]> statistic =
            new AbstractMap.SimpleEntry<>("name1", new double[] {1.0, 2.0, 3.0, 4.0});

        StatsCollector collector = new StatsCollector();

        int countProducers = 10_000;
        Thread[] producers = new Thread[countProducers];


        CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < countProducers; i++) {
            producers[i] = new Thread(() -> {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                collector.push(statistic.getKey(), statistic.getValue());
            });
            producers[i].start();
        }

        //Act
        latch.countDown();
        for (var producer : producers) {
            producer.join();
        }

        List<Metric> result = collector.stats();

        //Assert
        for (var metric : result) {

            Assertions.assertEquals(expected.name(), metric.name());
            Assertions.assertEquals(expected.sum(), metric.sum(), COMPARING_DELTA);
            Assertions.assertEquals(expected.average(), metric.average(), COMPARING_DELTA);
            Assertions.assertEquals(expected.min(), metric.min(), COMPARING_DELTA);
            Assertions.assertEquals(expected.max(), metric.max(), COMPARING_DELTA);
        }
    }
}
