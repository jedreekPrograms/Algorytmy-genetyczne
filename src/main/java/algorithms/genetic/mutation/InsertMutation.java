package algorithms.genetic.mutation;

import algorithms.genetic.Individual;

import java.util.Random;

public class InsertMutation {

    private final Random random;

    private long mutations;

    public InsertMutation() {

        this.random = new Random();

        this.mutations = 0;
    }

    public void mutate(Individual individual) {

        mutations++;

        int size = individual.size();

        int from = random.nextInt(size);

        int to = random.nextInt(size);

        while (from == to) {

            to = random.nextInt(size);
        }

        int[] chromosome = individual.getChromosomeReference();

        int value = chromosome[from];

        if (from < to) {

            System.arraycopy(
                    chromosome,
                    from + 1,
                    chromosome,
                    from,
                    to - from
            );
        }

        else {

            System.arraycopy(
                    chromosome,
                    to,
                    chromosome,
                    to + 1,
                    from - to
            );
        }

        chromosome[to] = value;

        individual.invalidate();
    }

    public long getMutations() {

        return mutations;
    }

    @Override
    public String toString() {

        return "InsertMutation{" +
                "mutations=" +
                mutations +
                '}';
    }
}