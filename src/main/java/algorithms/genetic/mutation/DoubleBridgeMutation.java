package algorithms.genetic.mutation;

import algorithms.genetic.Individual;

import java.util.Random;

public class DoubleBridgeMutation {

    private final Random random;

    /*
        statistics
     */
    private long mutations;

    public DoubleBridgeMutation() {

        this.random =
                new Random();

        this.mutations = 0;
    }

    public void mutate(
            Individual individual) {

        mutations++;

        int[] route =
                individual.getChromosomeReference();

        int n =
                route.length;

        /*
            too small
         */
        if (n < 8) {
            return;
        }

        /*
            split points
         */
        int pos1 =
                1 + random.nextInt(n / 4);

        int pos2 =
                pos1 + 1
                        + random.nextInt(n / 4);

        int pos3 =
                pos2 + 1
                        + random.nextInt(n / 4);

        /*
            safety
         */
        if (pos3 >= n) {
            return;
        }

        int[] newRoute =
                new int[n];

        int index = 0;

        /*
            A
         */
        for (int i = 0;
             i < pos1;
             i++) {

            newRoute[index++] =
                    route[i];
        }

        /*
            D
         */
        for (int i = pos3;
             i < n;
             i++) {

            newRoute[index++] =
                    route[i];
        }

        /*
            C
         */
        for (int i = pos2;
             i < pos3;
             i++) {

            newRoute[index++] =
                    route[i];
        }

        /*
            B
         */
        for (int i = pos1;
             i < pos2;
             i++) {

            newRoute[index++] =
                    route[i];
        }

        /*
            fallback safety
         */
        if (index != n) {
            return;
        }

        individual.setChromosome(
                newRoute
        );

        individual.invalidate();
    }

    public long getMutations() {

        return mutations;
    }

    @Override
    public String toString() {

        return "DoubleBridgeMutation{" +
                "mutations=" +
                mutations +
                '}';
    }
}