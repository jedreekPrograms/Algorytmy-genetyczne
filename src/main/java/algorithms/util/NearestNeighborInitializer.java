package algorithms.util;

import algorithms.model.TSPInstance;

import java.util.Random;

public class NearestNeighborInitializer {

    private static final Random random =
            new Random();

    public static int[] generate(
            TSPInstance instance) {

        int n =
                instance.getDimension();

        int[] route =
                new int[n];

        boolean[] visited =
                new boolean[n];

        /*
            random start
         */
        int current =
                random.nextInt(n);

        route[0] = current;

        visited[current] = true;

        /*
            nearest neighbor
         */
        for (int i = 1;
             i < n;
             i++) {

            int next = -1;

            double bestDistance =
                    Double.MAX_VALUE;

            for (int city = 0;
                 city < n;
                 city++) {

                if (visited[city]) {
                    continue;
                }

                double distance =
                        instance.getDistance(
                                current,
                                city
                        );

                if (distance
                        < bestDistance) {

                    bestDistance =
                            distance;

                    next = city;
                }
            }

            route[i] = next;

            visited[next] = true;

            current = next;
        }

        return route;
    }
}