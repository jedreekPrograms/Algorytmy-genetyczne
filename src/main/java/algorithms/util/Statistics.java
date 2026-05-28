package algorithms.util;

import java.util.List;

public class Statistics {

    public static double average(List<Double> values) {

        if (values.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;

        for (double value : values) {

            sum += value;
        }

        return sum / values.size();
    }

    public static double best(
            List<Double> values) {

        if (values.isEmpty()) {
            return 0.0;
        }

        double best = Double.MAX_VALUE;

        for (double value : values) {

            if (value < best) {
                best = value;
            }
        }

        return best;
    }

    public static double worst(List<Double> values) {

        if (values.isEmpty()) {
            return 0.0;
        }

        double worst = Double.MIN_VALUE;

        for (double value : values) {

            if (value > worst) {
                worst = value;
            }
        }

        return worst;
    }

    public static double standardDeviation(List<Double> values) {

        if (values.isEmpty()) {
            return 0.0;
        }

        double avg = average(values);

        double sum = 0.0;

        for (double value : values) {

            double diff = value - avg;

            sum += diff * diff;
        }

        return Math.sqrt(sum / values.size()
        );
    }

    public static double median(List<Double> values) {

        if (values.isEmpty()) {
            return 0.0;
        }

        values.sort(Double::compareTo);

        int size = values.size();

        if (size % 2 == 0) {

            return (values.get(size / 2 - 1) + values.get(size / 2)) / 2.0;
        }

        return values.get(size / 2);
    }
}