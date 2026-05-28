package algorithms.localsearch.candidates;

import algorithms.model.TSPInstance;

import java.util.Arrays;
import java.util.Comparator;

public class CandidateList {

    private final int[][] candidates;

    private final int candidateCount;

    private final int dimension;

    private long buildTimeMs;

    public CandidateList(TSPInstance instance, int candidateCount) {

        long start = System.currentTimeMillis();

        this.dimension = instance.getDimension();

        this.candidateCount = Math.min(candidateCount, dimension - 1);

        this.candidates = new int[dimension][this.candidateCount];

        build(instance);

        this.buildTimeMs = System.currentTimeMillis() - start;
    }

    private void build(TSPInstance instance) {

        for (int city = 0; city < dimension; city++) {

            buildForCity(instance, city);
        }
    }

    private void buildForCity(TSPInstance instance, int city) {

        Integer[] neighbors = new Integer[dimension - 1];

        int index = 0;

        for (int other = 0; other < dimension; other++) {

            if (city == other) {
                continue;
            }

            neighbors[index++] = other;
        }

        Arrays.sort(neighbors, Comparator.comparingDouble(x -> instance.getDistance(city, x)));

        for (int i = 0; i < candidateCount; i++) {

            candidates[city][i] = neighbors[i];
        }
    }

    public int[] getCandidates(
            int city) {

        return candidates[city];
    }

    public int getCandidateCount() {

        return candidateCount;
    }

    public int getDimension() {

        return dimension;
    }

    public long getBuildTimeMs() {

        return buildTimeMs;
    }

    @Override
    public String toString() {

        return "CandidateList{" +
                "dimension=" + dimension +
                ", candidateCount=" +
                candidateCount +
                ", buildTimeMs=" +
                buildTimeMs +
                '}';
    }
}