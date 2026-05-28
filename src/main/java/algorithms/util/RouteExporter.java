package algorithms.util;

import algorithms.genetic.Individual;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
//
public class RouteExporter {

    public static void exportRoute(Individual individual, String instanceName, String algorithm) throws IOException {

        File directory = new File("best_routes");

        if (!directory.exists()) {

            directory.mkdirs();
        }

        String filename =
                "best_routes/"
                        + instanceName.replace(
                        ".tsp",
                        ""
                )
                        + "_"
                        + algorithm
                        + ".txt";

        FileWriter writer = new FileWriter(filename);

        writer.write(
                "INSTANCE: "
                        + instanceName
                        + "\n"
        );

        writer.write(
                "ALGORITHM: "
                        + algorithm
                        + "\n"
        );

        writer.write(
                "DISTANCE: "
                        + String.format(
                        "%.2f",
                        individual.getDistanceValue()
                )
                        + "\n"
        );

        writer.write(
                "CREATED_TIME: "
                        + individual.getCreatedTime()
                        + "\n"
        );

        writer.write(
                "EVALUATIONS: "
                        + individual.getEvaluations()
                        + "\n\n"
        );

        writer.write(
                "ROUTE:\n"
        );

        int[] route = individual.getChromosomeReference();

        for (int city : route) {

            writer.write(
                    city + " "
            );
        }

        writer.write("\n");

        writer.close();
    }
}