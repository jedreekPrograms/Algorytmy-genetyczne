package algorithms.genetic.island;

import algorithms.genetic.GeneticAlgorithm;
import algorithms.genetic.Individual;
import algorithms.model.TSPInstance;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;

public class IslandGeneticAlgorithm {

    private final int islands;

    private final int migrationInterval;

    private final int migrantsCount;

    /*
        statistics
     */
    private long migrations;

    private long totalTimeMs;

    public IslandGeneticAlgorithm() {

        this.islands = 4;

        this.migrationInterval = 1;

        this.migrantsCount = 1;

        this.migrations = 0;

        this.totalTimeMs = 0;
    }

    public Individual solve(TSPInstance instance) throws Exception {

        long start = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(islands);

        List<Individual> islandBest = new ArrayList<>();

        List<Future<Individual>> futures = new ArrayList<>();

        for (int island = 0; island < islands; island++) {

            futures.add(executor.submit(() -> {

                        GeneticAlgorithm ga = new GeneticAlgorithm(instance);

                        return ga.solve(instance);
                    })
            );
        }

        for (Future<Individual> future : futures) {

            islandBest.add(future.get());
        }

        for (int epoch = 0; epoch < migrationInterval; epoch++) {

            migrate(islandBest);

            migrations++;
        }

        executor.shutdown();

        Individual best = islandBest.stream().min(
                                Comparator.comparingDouble(
                                        x -> x.getDistance(instance)
                                )
                        ).orElse(null);

        totalTimeMs = System.currentTimeMillis() - start;

        return best;
    }

    private void migrate(List<Individual> islands) {

        if (islands.size() < 2) {
            return;
        }

        for (int i = 0; i < islands.size(); i++) {

            int next = (i + 1) % islands.size();

            Individual migrant = new Individual(islands.get(i));

            islands.set(next, migrant);
        }
    }

    public long getMigrations() {

        return migrations;
    }

    public long getTotalTimeMs() {

        return totalTimeMs;
    }

    @Override
    public String toString() {

        return "IslandGeneticAlgorithm{" +
                "islands=" + islands +
                ", migrations=" + migrations +
                ", totalTimeMs=" + totalTimeMs +
                '}';
    }
}