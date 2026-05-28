package algorithms.benchmark;

import algorithms.genetic.GeneticAlgorithm;
import algorithms.genetic.Individual;
import algorithms.genetic.island.IslandGeneticAlgorithm;
import algorithms.model.TSPInstance;
import algorithms.parser.TSPParser;
import algorithms.util.CSVWriter;
import algorithms.util.RouteExporter;
import algorithms.util.Statistics;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelBenchmarkRunner {

    private static final int RUNS = 100;

    private static final String[] INSTANCES = {

//            "src/main/resources/djibouti.tsp",
//            "src/main/resources/qatar.tsp",
//            "src/main/resources/western_sahara.tsp",
//            "src/main/resources/zimbabwe.tsp",
//            "src/main/resources/oman.tsp",
//            "src/main/resources/ireland.tsp",
//            "src/main/resources/canada.tsp",
            "src/main/resources/tanzania.tsp",
            "src/main/resources/uruguay.tsp",
            "src/main/resources/egypt.tsp"
    };

    private final CSVWriter csvWriter;

    public ParallelBenchmarkRunner() throws Exception {

        this.csvWriter = new CSVWriter("benchmark_results.csv");

        csvWriter.resetFile();
    }

    public void run() throws Exception {

        for (String instancePath : INSTANCES) {

            runGeneticAlgorithm(instancePath);

            runIslandGeneticAlgorithm(instancePath);
        }
    }

    private void runGeneticAlgorithm(String instancePath) throws Exception {

        System.out.println("\n======================================");

        System.out.println("RUNNING GA -> " + instancePath);

        TSPInstance instance = TSPParser.parse(instancePath);

        //parralelism
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        List<Future<Result>> futures = new ArrayList<>();

        long wallClockStart = System.currentTimeMillis();

        for (int run = 0; run < RUNS; run++) {

            final int currentRun = run;

            futures.add(executor.submit(() -> {

                        long start = System.currentTimeMillis();

                        GeneticAlgorithm ga = new GeneticAlgorithm(instance);

                        Individual best = ga.solve(instance);

                        long time = System.currentTimeMillis() - start;

                        System.out.println("[GA] RUN " + (currentRun + 1)
                                + "/" + RUNS + " -> " + String.format("%.2f", best.getDistance(instance))
                                        + " | "
                                        + time
                                        + " ms"
                        );

                        return new Result(best, time);
                    })
            );
        }

        List<Double> distances = new ArrayList<>();

        long cpuTime = 0;

        Individual globalBest = null;

        for (Future<Result> future : futures) {

            //blokuje program az dany run sie skonczy
            Result result = future.get();

            double distance = result.best.getDistance(instance);

            distances.add(distance);

            cpuTime += result.timeMs;

            if (globalBest == null || distance < globalBest.getDistance(instance)) {

                globalBest = result.best;
            }
        }

        //zamykamy thread poll
        executor.shutdown();

        long wallClockTime = System.currentTimeMillis() - wallClockStart;

        printStatistics(
                "GA",
                distances,
                cpuTime,
                wallClockTime
        );

        csvWriter.append(
                new File(instancePath).getName(),
                "GA",
                Statistics.best(distances),
                Statistics.average(distances),
                Statistics.worst(distances),
                Statistics.standardDeviation(
                        distances
                ),
                cpuTime,
                wallClockTime
        );

        RouteExporter.exportRoute(
                globalBest,
                new File(instancePath).getName(),
                "GA"
        );
    }

    //mamy kilka wysp populacji
    private void runIslandGeneticAlgorithm(String instancePath) throws Exception {

        System.out.println("\n======================================");

        System.out.println("RUNNING ISLAND GA -> " + instancePath);

        TSPInstance instance = TSPParser.parse(instancePath);

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        List<Future<Result>> futures = new ArrayList<>();

        long wallClockStart = System.currentTimeMillis();

        for (int run = 0; run < RUNS; run++) {

            final int currentRun = run;

            futures.add(executor.submit(() -> {

                        long start = System.currentTimeMillis();

                        IslandGeneticAlgorithm ga = new IslandGeneticAlgorithm();

                        Individual best = ga.solve(instance);

                        long time = System.currentTimeMillis() - start;

                        System.out.println(
                                "[ISLAND] RUN "
                                        + (currentRun + 1)
                                        + "/"
                                        + RUNS
                                        + " -> "
                                        + String.format(
                                        "%.2f",
                                        best.getDistance(instance)
                                )
                                        + " | "
                                        + time
                                        + " ms"
                        );

                        return new Result(best, time);
                    })
            );
        }

        List<Double> distances = new ArrayList<>();

        long cpuTime = 0;

        Individual globalBest = null;

        for (Future<Result> future : futures) {

            Result result = future.get();

            double distance = result.best.getDistance(instance);

            distances.add(distance);

            cpuTime += result.timeMs;

            if (globalBest == null || distance < globalBest.getDistance(instance)) {

                globalBest = result.best;
            }
        }

        executor.shutdown();

        long wallClockTime = System.currentTimeMillis() - wallClockStart;

        printStatistics(
                "ISLAND_GA",
                distances,
                cpuTime,
                wallClockTime
        );

        csvWriter.append(
                new File(instancePath).getName(),
                "ISLAND_GA",
                Statistics.best(distances),
                Statistics.average(distances),
                Statistics.worst(distances),
                Statistics.standardDeviation(
                        distances
                ),
                cpuTime,
                wallClockTime
        );

        RouteExporter.exportRoute(
                globalBest,
                new File(instancePath).getName(),
                "ISLAND_GA"
        );
    }

    private void printStatistics(
            String algorithm,
            List<Double> distances,
            long cpuTime,
            long wallClockTime) {

        System.out.println("\n========== " + algorithm + " ==========");

        System.out.println(
                "BEST: "
                        + String.format(
                        "%.2f",
                        Statistics.best(distances)
                )
        );

        System.out.println(
                "AVERAGE: "
                        + String.format(
                        "%.2f",
                        Statistics.average(distances)
                )
        );

        System.out.println(
                "WORST: "
                        + String.format(
                        "%.2f",
                        Statistics.worst(distances)
                )
        );

        System.out.println(
                "STD DEV: "
                        + String.format(
                        "%.2f",
                        Statistics.standardDeviation(
                                distances
                        )
                )
        );

        System.out.println("CPU TIME [ms]: " + cpuTime);

        System.out.println("WALL CLOCK TIME [ms]: " + wallClockTime);

        System.out.println("AVG TIME/RUN [ms]: " + (wallClockTime / RUNS));
    }
}