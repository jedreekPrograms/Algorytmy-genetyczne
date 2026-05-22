package algorithms.genetic.crossover;

import algorithms.genetic.Individual;

import java.util.Arrays;
import java.util.Random;

public class OrderCrossover {

    private final Random random;

    /*
        statistics
     */
    private long crossovers;

    public OrderCrossover() {

        this.random =
                new Random();

        this.crossovers = 0;
    }

    public Individual crossover(
            Individual parent1,
            Individual parent2) {

        crossovers++;

        int size =
                parent1.size();

        int[] child =
                new int[size];

        boolean[] used =
                new boolean[size];

        Arrays.fill(child, -1);

        /*
            crossover points
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
            copy fragment from parent1
         */
        for (int i = left;
             i <= right;
             i++) {

            int gene =
                    parent1.getGene(i);

            child[i] = gene;

            used[gene] = true;
        }

        /*
            fill remaining from parent2
         */
        int currentIndex =
                (right + 1) % size;

        for (int i = 0;
             i < size;
             i++) {

            int gene =
                    parent2.getGene(
                            (right + 1 + i)
                                    % size
                    );

            if (!used[gene]) {

                child[currentIndex] =
                        gene;

                used[gene] = true;

                currentIndex =
                        (currentIndex + 1)
                                % size;
            }
        }

        return new Individual(child);
    }

    public long getCrossovers() {

        return crossovers;
    }

    @Override
    public String toString() {

        return "OrderCrossover{" +
                "crossovers=" +
                crossovers +
                '}';
    }
}