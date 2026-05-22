package algorithms.genetic.selection;

import algorithms.genetic.Individual;
import algorithms.genetic.Population;
import algorithms.model.TSPInstance;

import java.util.Random;

public class TournamentSelection {

    private final int tournamentSize;

    private final Random random;

    /*
        statistics
     */
    private long selections;

    public TournamentSelection(
            int tournamentSize) {

        this.tournamentSize =
                tournamentSize;

        this.random =
                new Random();

        this.selections = 0;
    }

    public Individual select(
            Population population,
            TSPInstance instance) {

        selections++;

        Individual best = null;

        for (int i = 0;
             i < tournamentSize;
             i++) {

            int index =
                    random.nextInt(
                            population.size()
                    );

            Individual candidate =
                    population.get(index);

            if (best == null
                    ||
                    candidate.getDistance(instance)
                            <
                            best.getDistance(instance)) {

                best = candidate;
            }
        }

        return new Individual(best);
    }

    public long getSelections() {

        return selections;
    }

    public int getTournamentSize() {

        return tournamentSize;
    }

    @Override
    public String toString() {

        return "TournamentSelection{" +
                "tournamentSize=" +
                tournamentSize +
                ", selections=" +
                selections +
                '}';
    }
}