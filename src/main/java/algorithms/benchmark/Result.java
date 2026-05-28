package algorithms.benchmark;

import algorithms.genetic.Individual;

public class Result {
//
    public final Individual best;

    public final long timeMs;

    public Result(Individual best, long timeMs) {

        this.best = best;
        this.timeMs = timeMs;
    }
}