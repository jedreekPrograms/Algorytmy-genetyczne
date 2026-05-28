package algorithms.genetic.crossover;

import algorithms.genetic.Individual;

import java.util.Arrays;
import java.util.Random;

public class PMXCrossover {

    private final Random random;

    private long crossovers;

    public PMXCrossover() {

        this.random = new Random();

        this.crossovers = 0;
    }

    public Individual crossover(Individual parent1, Individual parent2) {

        crossovers++;

        int size = parent1.size();

        int[] child = new int[size];

        Arrays.fill(child, -1);

        int left = random.nextInt(size);

        int right = random.nextInt(size);

        if (left > right) {

            int temp = left;

            left = right;

            right = temp;
        }

        for (int i = left; i <= right; i++) {

            child[i] = parent1.getGene(i);
        }

        for (int i = left; i <= right; i++) {

            int gene = parent2.getGene(i);

            if (!contains(child, gene)) {

                int position = i;

                while (true) {

                    int mappedGene = parent1.getGene(position);

                    position = findIndex(parent2, mappedGene);

                    if (child[position] == -1) {

                        child[position] = gene;

                        break;
                    }
                }
            }
        }

        for (int i = 0; i < size; i++) {

            if (child[i] == -1) {

                child[i] = parent2.getGene(i);
            }
        }

        return new Individual(child);
    }

    private boolean contains(int[] array, int value) {

        for (int x : array) {

            if (x == value) {
                return true;
            }
        }

        return false;
    }

    private int findIndex(Individual individual, int value) {

        for (int i = 0; i < individual.size(); i++) {

            if (individual.getGene(i) == value) {

                return i;
            }
        }

        return -1;
    }

    public long getCrossovers() {

        return crossovers;
    }

    @Override
    public String toString() {

        return "PMXCrossover{" +
                "crossovers=" +
                crossovers +
                '}';
    }
}