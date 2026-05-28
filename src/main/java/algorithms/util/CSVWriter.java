package algorithms.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {

    private final String path;

    public CSVWriter(String path) {

        this.path = path;
    }

    public void resetFile()
            throws IOException {

        File file = new File(path);

        if (file.exists()) {
            file.delete();
        }

        FileWriter writer = new FileWriter(path);

        writer.write(
                "instance," +
                        "algorithm," +
                        "best," +
                        "average," +
                        "worst," +
                        "std_dev," +
                        "cpu_time_ms," +
                        "wall_clock_time_ms\n"
        );

        writer.close();
    }

    public void append(
            String instance,
            String algorithm,
            double best,
            double average,
            double worst,
            double stdDev,
            long cpuTime,
            long wallClockTime)
            throws IOException {

        FileWriter writer = new FileWriter(path, true);

        writer.write(
                instance + "," +
                        algorithm + "," +
                        format(best) + "," +
                        format(average) + "," +
                        format(worst) + "," +
                        format(stdDev) + "," +
                        cpuTime + "," +
                        wallClockTime + "\n"
        );

        writer.close();
    }

    private String format(double value) {

        return String.format(
                "%.2f",
                value
        );
    }
}