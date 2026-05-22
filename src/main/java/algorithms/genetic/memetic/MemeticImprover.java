package algorithms.genetic.memetic;

import algorithms.genetic.Individual;
import algorithms.localsearch.TwoOptLocalSearch;
import algorithms.model.TSPInstance;

public class MemeticImprover {

    private final TwoOptLocalSearch localSearch;

    private final int maxIterations;

    /*
        statistics
     */
    private long improvements;

    private long totalTimeMs;

    public MemeticImprover(
            TSPInstance instance,
            int maxIterations,
            int candidateCount) {

        this.maxIterations =
                maxIterations;

        this.localSearch =
                new TwoOptLocalSearch(
                        instance,
                        candidateCount
                );

        this.improvements = 0;

        this.totalTimeMs = 0;
    }

    public void improve(
            Individual individual,
            TSPInstance instance) {

        long start =
                System.currentTimeMillis();

        double before =
                individual.getDistance(instance);

        localSearch.improve(
                individual,
                instance,
                maxIterations
        );

        double after =
                individual.getDistance(instance);

        if (after < before) {
            improvements++;
        }

        totalTimeMs +=
                System.currentTimeMillis()
                        - start;
    }

    public long getImprovements() {

        return improvements;
    }

    public long getTotalTimeMs() {

        return totalTimeMs;
    }

    public double getAverageTimeMs() {

        if (improvements == 0) {
            return 0.0;
        }

        return (double)
                totalTimeMs
                / improvements;
    }

    @Override
    public String toString() {

        return "MemeticImprover{" +
                "improvements=" +
                improvements +
                ", totalTimeMs=" +
                totalTimeMs +
                '}';
    }
}