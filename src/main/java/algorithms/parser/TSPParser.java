package algorithms.parser;

import algorithms.model.City;
import algorithms.model.TSPInstance;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TSPParser {

    public static TSPInstance parse(String path) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(path));

        List<City> cities = new ArrayList<>();

        String line;

        boolean nodeSection = false;

        while ((line = reader.readLine())
                != null) {

            line = line.trim();

            if (line.isEmpty()) {
                continue;
            }

            if (line.equals(
                    "NODE_COORD_SECTION")) {

                nodeSection = true;

                continue;
            }

            if (line.equals("EOF")) {
                break;
            }

            if (!nodeSection) {
                continue;
            }

            String[] parts = line.split("\\s+");

            if (parts.length < 3) {
                continue;
            }

            int id = Integer.parseInt(parts[0]) - 1;

            double x = Double.parseDouble(parts[1]);

            double y = Double.parseDouble(parts[2]);

            cities.add(new City(id, x, y));
        }

        reader.close();

        return new TSPInstance(cities);
    }
}