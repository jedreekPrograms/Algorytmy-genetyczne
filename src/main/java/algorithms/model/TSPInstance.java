package algorithms.model;

import java.util.List;

public class TSPInstance {

    private final List<City> cities;

    private final double[][] distances;

    private final int dimension;

    public TSPInstance(List<City> cities) {

        this.cities = cities;

        this.dimension = cities.size();

        this.distances = new double[dimension][dimension];

        computeDistances();
    }

    private void computeDistances() {

        for (int i = 0; i < dimension; i++) {

            for (int j = i; j < dimension; j++) {

                if (i == j) {

                    distances[i][j] = 0.0;

                    continue;
                }

                City a = cities.get(i);

                City b = cities.get(j);

                double dx = a.getX() - b.getX();

                double dy = a.getY() - b.getY();

                double distance = Math.sqrt(dx * dx + dy * dy);

                distances[i][j] = distance;

                distances[j][i] = distance;
            }
        }
    }

    public double getDistance(int a, int b) {

        return distances[a][b];
    }

    public int getDimension() {

        return dimension;
    }

    public List<City> getCities() {

        return cities;
    }

    @Override
    public String toString() {

        return "TSPInstance{" + "dimension=" + dimension + '}';
    }
}