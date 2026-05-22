package algorithms.genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Population {

    private final List<Individual> individuals;

    private Individual cachedBest;

    private Individual cachedWorst;

    private double cachedAverage;

    private boolean dirty = true;

    private final long creationTime;

    public Population() {

        this.individuals =
                new ArrayList<>();

        this.creationTime =
                System.currentTimeMillis();
    }

    public Population(
            List<Individual> individuals) {

        this.individuals =
                individuals;

        this.creationTime =
                System.currentTimeMillis();

        this.dirty = true;
    }

    public void add(
            Individual individual) {

        individuals.add(individual);

        dirty = true;
    }

    public List<Individual> getIndividuals() {

        return individuals;
    }

    public int size() {

        return individuals.size();
    }

    public Individual get(int index) {

        return individuals.get(index);
    }

    public void sort() {

        Collections.sort(individuals);
    }

    public Individual getBest() {

        if (!dirty
                && cachedBest != null) {

            return cachedBest;
        }

        Individual best =
                individuals.get(0);

        for (int i = 1;
             i < individuals.size();
             i++) {

            if (individuals.get(i)
                    .getDistanceValue()
                    <
                    best.getDistanceValue()) {

                best =
                        individuals.get(i);
            }
        }

        cachedBest = best;

        return best;
    }

    public Individual getWorst() {

        if (!dirty
                && cachedWorst != null) {

            return cachedWorst;
        }

        Individual worst =
                individuals.get(0);

        for (int i = 1;
             i < individuals.size();
             i++) {

            if (individuals.get(i)
                    .getDistanceValue()
                    >
                    worst.getDistanceValue()) {

                worst =
                        individuals.get(i);
            }
        }

        cachedWorst = worst;

        return worst;
    }

    public double getAverageDistance() {

        if (!dirty
                && cachedAverage > 0) {

            return cachedAverage;
        }

        double sum = 0.0;

        for (Individual individual
                : individuals) {

            sum +=
                    individual.getDistanceValue();
        }

        cachedAverage =
                sum / individuals.size();

        dirty = false;

        return cachedAverage;
    }

    public double getDiversity() {

        if (individuals.size() < 2) {
            return 0.0;
        }

        double average =
                getAverageDistance();

        double sum = 0.0;

        for (Individual individual
                : individuals) {

            double diff =
                    individual.getDistanceValue()
                            - average;

            sum += diff * diff;
        }

        return Math.sqrt(
                sum / individuals.size()
        );
    }

    public long getCreationTime() {

        return creationTime;
    }

    @Override
    public String toString() {

        return "Population{" +
                "size=" + individuals.size() +
                ", best=" +
                getBest().getDistanceValue() +
                ", average=" +
                getAverageDistance() +
                ", diversity=" +
                getDiversity() +
                '}';
    }
}