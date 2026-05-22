package algorithms.genetic.selection;

import algorithms.genetic.Individual;
import algorithms.genetic.Population;
import algorithms.model.TSPInstance;

import java.util.Random;

public class RouletteSelection {

    private final Random random;

    /*
        statistics
     */
    private long selections;

    public RouletteSelection() {

        this.random =
                new Random();

        this.selections = 0;
    }

    public Individual select(
            Population population,
            TSPInstance instance) {

        selections++;

        double fitnessSum = 0.0;

        /*
            fitness = 1 / distance
         */
        for (Individual individual
                : population.getIndividuals()) {

            double distance =
                    individual.getDistance(instance);

            fitnessSum +=
                    1.0 / distance;
        }

        double randomValue =
                random.nextDouble()
                        * fitnessSum;

        double current = 0.0;

        for (Individual individual
                : population.getIndividuals()) {

            current +=
                    1.0
                            /
                            individual.getDistance(instance);

            if (current >= randomValue) {

                return new Individual(
                        individual
                );
            }
        }

        /*
            fallback
         */
        return new Individual(
                population.getBest()
        );
    }

    public long getSelections() {

        return selections;
    }

    @Override
    public String toString() {

        return "RouletteSelection{" +
                "selections=" +
                selections +
                '}';
    }
}