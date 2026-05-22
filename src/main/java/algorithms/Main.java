package algorithms;

import algorithms.benchmark.ParallelBenchmarkRunner;

public class Main {

    public static void main(String[] args)
            throws Exception {

        long start =
                System.currentTimeMillis();

        ParallelBenchmarkRunner runner =
                new ParallelBenchmarkRunner();

        runner.run();

        long totalTime =
                System.currentTimeMillis()
                        - start;

        System.out.println(
                "\n===================================="
        );

        System.out.println(
                "ALL BENCHMARKS FINISHED"
        );

        System.out.println(
                "TOTAL TIME [ms]: "
                        + totalTime
        );

        System.out.println(
                "TOTAL TIME [s]: "
                        + (totalTime / 1000.0)
        );
    }
}