package algorithms.localsearch;

import algorithms.genetic.Individual;
import algorithms.localsearch.neighborhood.TwoOptMove;
import algorithms.localsearch.neighborhood.TwoOptNeighborhood;
import algorithms.model.TSPInstance;

public class TwoOptLocalSearch {

    private final TwoOptNeighborhood neighborhood;

    /*
        statistics
     */
    private long performedMoves;

    private long localSearchCalls;

    private long totalIterations;

    private long totalTimeMs;

    public TwoOptLocalSearch(
            TSPInstance instance,
            int candidateCount) {

        this.neighborhood =
                new TwoOptNeighborhood(
                        instance,
                        candidateCount
                );

        this.performedMoves = 0;

        this.localSearchCalls = 0;

        this.totalIterations = 0;

        this.totalTimeMs = 0;
    }

    public void improve(
            Individual individual,
            TSPInstance instance,
            int maxIterations) {

        long start =
                System.currentTimeMillis();

        localSearchCalls++;

        int[] route =
                individual.getChromosomeReference();

        int iteration = 0;

        int noImprovementCounter = 0;

        while (iteration < maxIterations) {

            iteration++;

            totalIterations++;

            TwoOptMove move =
                    neighborhood.findMove(
                            route,
                            instance
                    );

            /*
                no improving move
             */
            if (move == null) {

                noImprovementCounter++;

                if (noImprovementCounter >= 2) {
                    break;
                }

                continue;
            }

            neighborhood.applyMove(
                    route,
                    move
            );

            performedMoves++;
        }

        individual.invalidate();

        totalTimeMs +=
                System.currentTimeMillis()
                        - start;
    }

    public long getPerformedMoves() {

        return performedMoves;
    }

    public long getLocalSearchCalls() {

        return localSearchCalls;
    }

    public long getTotalIterations() {

        return totalIterations;
    }

    public long getTotalTimeMs() {

        return totalTimeMs;
    }

    public double getAverageIterations() {

        if (localSearchCalls == 0) {
            return 0.0;
        }

        return (double)
                totalIterations
                / localSearchCalls;
    }

    public double getAverageTimeMs() {

        if (localSearchCalls == 0) {
            return 0.0;
        }

        return (double)
                totalTimeMs
                / localSearchCalls;
    }

    @Override
    public String toString() {

        return "TwoOptLocalSearch{" +
                "performedMoves=" +
                performedMoves +
                ", localSearchCalls=" +
                localSearchCalls +
                ", totalIterations=" +
                totalIterations +
                ", totalTimeMs=" +
                totalTimeMs +
                '}';
    }
}