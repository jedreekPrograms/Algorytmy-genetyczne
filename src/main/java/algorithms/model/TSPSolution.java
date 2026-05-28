package algorithms.model;

import java.util.Arrays;

public class TSPSolution {

    private final int[] route;

    private final double distance;

    private final long timeMs;

    public TSPSolution(int[] route, double distance, long timeMs) {

        this.route = route.clone();

        this.distance = distance;

        this.timeMs = timeMs;
    }

    public int[] getRoute() {

        return route.clone();
    }

    public double getDistance() {

        return distance;
    }

    public long getTimeMs() {

        return timeMs;
    }

    @Override
    public String toString() {

        return "TSPSolution{" +
                "distance=" + distance +
                ", timeMs=" + timeMs +
                ", route=" +
                Arrays.toString(route) +
                '}';
    }
}