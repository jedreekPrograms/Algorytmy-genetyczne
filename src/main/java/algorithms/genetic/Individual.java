package algorithms.genetic;

import algorithms.model.TSPInstance;

import java.util.Arrays;

public class Individual implements Comparable<Individual> {

    //zapis trasy permutajca, chromosome[i] gen
    private int[] chromosome;

    private double distance;

    private boolean dirty = true;

    private long evaluations;

    private final long createdTime;

    public Individual(int[] chromosome) {

        this.chromosome = chromosome.clone();

        this.createdTime = System.currentTimeMillis();
    }

    public Individual(Individual other) {

        this.chromosome = other.chromosome.clone();

        this.distance = other.distance;

        this.dirty = other.dirty;

        this.evaluations = other.evaluations;

        this.createdTime = other.createdTime;
    }

    public int[] getChromosome() {

        return chromosome.clone();
    }

    public int[] getChromosomeReference() {

        return chromosome;
    }

    public void setChromosome(int[] chromosome) {

        this.chromosome = chromosome.clone();

        //po mutation crossover trasa sie zmienia, liczymy fitness bo stary discance bledny
        //cached fitness niekatulany
        //z cache jesli sie nie zmienil mamy gotowy wynik
        this.dirty = true;
    }

    public int size() {

        return chromosome.length;
    }

    public int getGene(int index) {

        //miasto na pozycji i
        return chromosome[index];
    }

    public void setGene(int index, int value) {

        chromosome[index] = value;

        dirty = true;
    }

    public double getDistance(TSPInstance instance) {

        //jesli sie nie zmienil zwracamy gootowy wynik
        if (!dirty) {
            return distance;
        }

        evaluations++;

        double total = 0.0;

        for (int i = 0; i < chromosome.length - 1; i++) {

            total += instance.getDistance(chromosome[i], chromosome[i + 1]);
        }

        total += instance.getDistance(chromosome[chromosome.length - 1], chromosome[0]
        );

        distance = total;

        dirty = false;

        return distance;
    }

    public double getDistanceValue() {

        return distance;
    }

    public long getEvaluations() {

        return evaluations;
    }

    public long getCreatedTime() {

        return createdTime;
    }

    public void invalidate() {

        dirty = true;
    }

    @Override
    public int compareTo(Individual other) {

        return Double.compare(this.distance, other.distance);
    }

    @Override
    public String toString() {

        return Arrays.toString(chromosome) + " distance=" + distance;
    }
}