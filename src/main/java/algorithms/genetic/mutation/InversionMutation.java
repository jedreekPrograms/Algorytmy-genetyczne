package algorithms.genetic.mutation;

import algorithms.genetic.Individual;

import java.util.Random;

public class InversionMutation {

    private final Random random;

    /*
        statistics
     */
    private long mutations;

    public InversionMutation() {

        this.random =
                new Random();

        this.mutations = 0;
    }

    public void mutate(
            Individual individual) {

        mutations++;

        int size =
                individual.size();

        /*
            choose segment
         */
        int left =
                random.nextInt(size);

        int right =
                random.nextInt(size);

        if (left > right) {

            int temp = left;

            left = right;

            right = temp;
        }

        /*
            avoid useless moves
         */
        if (left == right) {
            return;
        }

        int[] chromosome =
                individual.getChromosomeReference();

        /*
            reverse segment
         */
        while (left < right) {

            int temp =
                    chromosome[left];

            chromosome[left] =
                    chromosome[right];

            chromosome[right] =
                    temp;

            left++;
            right--;
        }

        individual.invalidate();
    }

    public long getMutations() {

        return mutations;
    }

    @Override
    public String toString() {

        return "InversionMutation{" +
                "mutations=" +
                mutations +
                '}';
    }
}