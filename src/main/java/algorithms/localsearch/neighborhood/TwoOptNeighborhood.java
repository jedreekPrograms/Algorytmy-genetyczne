package algorithms.localsearch.neighborhood;

import algorithms.localsearch.candidates.CandidateList;
import algorithms.model.TSPInstance;

public class TwoOptNeighborhood {

    private final CandidateList candidateList;

    private long evaluatedMoves;

    private long improvingMoves;

    public TwoOptNeighborhood(TSPInstance instance, int candidateCount) {

        this.candidateList = new CandidateList(instance, candidateCount);
    }

    public TwoOptMove findMove(int[] route, TSPInstance instance) {

        int n = route.length;

        int[] position = new int[n];

        for (int i = 0; i < n; i++) {

            position[route[i]] = i;
        }

        TwoOptMove bestMove = null;

        double bestDelta = 0.0;

        for (int i = 0; i < n; i++) {

            int cityA = route[i];

            int cityB = route[(i + 1) % n];

            int[] candidates = candidateList.getCandidates(cityA);

            for (int candidate : candidates) {

                int j = position[candidate];

                if (j == i) {
                    continue;
                }

                if ((i + 1) % n == j) {
                    continue;
                }

                if ((j + 1) % n == i) {
                    continue;
                }

                int next = (j + 1) % n;

                int cityC = route[j];

                int cityD = route[next];

                double before = instance.getDistance(cityA, cityB) + instance.getDistance(cityC, cityD);

                double after = instance.getDistance(cityA, cityC) + instance.getDistance(cityB, cityD);

                double delta = after - before;

                evaluatedMoves++;

                if (delta < bestDelta) {

                    improvingMoves++;

                    int left = Math.min(i + 1, j);

                    int right = Math.max(i + 1, j);

                    if (left < 0 || right >= n || left >= right) {

                        continue;
                    }

                    bestDelta = delta;

                    bestMove = new TwoOptMove(left, right, delta);
                }
            }
        }

        return bestMove;
    }

    public void applyMove(int[] route, TwoOptMove move) {

        reverse(route, move.getLeft(), move.getRight());
    }

    private void reverse(int[] route, int left, int right) {

        while (left < right) {

            int temp = route[left];

            route[left] = route[right];

            route[right] = temp;

            left++;
            right--;
        }
    }

}