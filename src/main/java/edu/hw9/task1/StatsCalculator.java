package edu.hw9.task1;

import java.util.concurrent.Callable;
import org.jetbrains.annotations.NotNull;

public class StatsCalculator implements Callable<Metric> {
    private final String name;
    private final double[] values;

    public StatsCalculator(@NotNull String name, double[] values) {
        this.name = name;
        this.values = values;
    }

    @Override
    public Metric call() {
        double sum = 0.0;
        double max = Double.NEGATIVE_INFINITY;
        double min = Double.POSITIVE_INFINITY;

        for (double value : values) {
            if (value > max) {
                max = value;
            }
            if (value < min) {
                min = value;
            }
            sum += value;
        }

        return new Metric(name, sum, sum / values.length, max, min);
    }
}
