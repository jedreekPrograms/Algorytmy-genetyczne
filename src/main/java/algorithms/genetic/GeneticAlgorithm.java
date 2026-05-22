package algorithms.genetic;

import algorithms.genetic.crossover.OrderCrossover;
import algorithms.genetic.crossover.PMXCrossover;
import algorithms.genetic.memetic.MemeticImprover;
import algorithms.genetic.mutation.DoubleBridgeMutation;
import algorithms.genetic.mutation.InsertMutation;
import algorithms.genetic.mutation.InversionMutation;
import algorithms.genetic.selection.TournamentSelection;
import algorithms.model.TSPInstance;
import algorithms.util.NearestNeighborInitializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm {

    private final Random random;

    private final GAParameters parameters;

    private final TournamentSelection selection;

    private final OrderCrossover ox;

    private final PMXCrossover pmx;

    private final InversionMutation inversionMutation;

    private final InsertMutation insertMutation;

    private final DoubleBridgeMutation doubleBridgeMutation;

    private final MemeticImprover memeticImprover;

    public GeneticAlgorithm(
            TSPInstance instance) {

        this.random =
                new Random();

        this.parameters =
                GAParameters.forProblemSize(
                        instance.getDimension()
                );

        this.selection =
                new TournamentSelection(
                        parameters.tournamentSize
                );

        this.ox =
                new OrderCrossover();

        this.pmx =
                new PMXCrossover();

        this.inversionMutation =
                new InversionMutation();

        this.insertMutation =
                new InsertMutation();

        this.doubleBridgeMutation =
                new DoubleBridgeMutation();

        this.memeticImprover =
                new MemeticImprover(
                        instance,
                        parameters.localSearchIterations,
                        parameters.candidateCount
                );
    }

    public Individual solve(
            TSPInstance instance) {

        Population population =
                initializePopulation(instance);

        evaluatePopulation(
                population,
                instance
        );

        population.sort();

        Individual globalBest =
                new Individual(
                        population.getBest()
                );

        int noImprovementCounter = 0;

        double mutationRate =
                parameters.mutationRate;

        for (int generation = 0;
             generation < parameters.generations;
             generation++) {

            Population newPopulation =
                    new Population();

            population.sort();

            /*
                strong memetic intensification
             */
            if (generation %
                    parameters.intensificationInterval
                    == 0) {

                int improveCount =
                        Math.min(
                                population.size() / 3,
                                parameters.intensificationCount
                        );

                for (int i = 0;
                     i < improveCount;
                     i++) {

                    memeticImprover.improve(
                            population.get(i),
                            instance
                    );
                }
            }

            /*
                elitism
             */
            for (int i = 0;
                 i < parameters.eliteSize;
                 i++) {

                newPopulation.add(
                        new Individual(
                                population.get(i)
                        )
                );
            }

            while (newPopulation.size()
                    < parameters.populationSize) {

                Individual parent1 =
                        selection.select(
                                population,
                                instance
                        );

                Individual parent2 =
                        selection.select(
                                population,
                                instance
                        );

                while (same(parent1, parent2)) {

                    parent2 =
                            selection.select(
                                    population,
                                    instance
                            );
                }

                Individual child;

                /*
                    crossover
                 */
                if (random.nextDouble()
                        < parameters.crossoverRate) {

                    if (random.nextBoolean()) {

                        child =
                                ox.crossover(
                                        parent1,
                                        parent2
                                );

                    } else {

                        child =
                                pmx.crossover(
                                        parent1,
                                        parent2
                                );
                    }

                } else {

                    child =
                            new Individual(parent1);
                }

                /*
                    mutation
                 */
                if (random.nextDouble()
                        < mutationRate) {

                    double type =
                            random.nextDouble();

                    if (noImprovementCounter > 25
                            && type < 0.50) {

                        doubleBridgeMutation
                                .mutate(child);

                    } else if (type < 0.50) {

                        insertMutation
                                .mutate(child);

                    } else {

                        inversionMutation
                                .mutate(child);
                    }
                }

                /*
                    memetic
                 */
                if (random.nextDouble()
                        < parameters.memeticProbability) {

                    memeticImprover.improve(
                            child,
                            instance
                    );
                }

                child.getDistance(instance);

                newPopulation.add(child);
            }

            /*
                immigrants
             */
            if (generation % 10 == 0) {

                for (int i = 0;
                     i < parameters.immigrantsCount;
                     i++) {

                    Individual immigrant =
                            new Individual(
                                    randomPermutation(
                                            instance.getDimension()
                                    )
                            );

                    immigrant.getDistance(instance);

                    int replaceIndex =
                            newPopulation.size()
                                    - 1
                                    - i;

                    newPopulation
                            .getIndividuals()
                            .set(
                                    replaceIndex,
                                    immigrant
                            );
                }
            }

            population = newPopulation;

            population.sort();

            Individual generationBest =
                    population.getBest();

            if (generationBest.getDistance(instance)
                    <
                    globalBest.getDistance(instance)) {

                globalBest =
                        new Individual(
                                generationBest
                        );

                noImprovementCounter = 0;

                mutationRate =
                        parameters.mutationRate;

            } else {

                noImprovementCounter++;

                /*
                    adaptive mutation
                 */
                mutationRate =
                        Math.min(
                                0.30,
                                mutationRate * 1.03
                        );

                /*
                    stagnation intensification
                 */
                if (noImprovementCounter > 30) {

                    for (int i = 0;
                         i < 3;
                         i++) {

                        memeticImprover.improve(
                                globalBest,
                                instance
                        );
                    }
                }
            }

            if (noImprovementCounter
                    >= parameters.noImprovementLimit) {

                break;
            }
        }

        return globalBest;
    }

    private Population initializePopulation(
            TSPInstance instance) {

        List<Individual> individuals =
                new ArrayList<>();

        int n =
                instance.getDimension();

        /*
            50% nearest neighbor
         */
        int heuristicCount =
                parameters.populationSize / 2;

        for (int i = 0;
             i < heuristicCount;
             i++) {

            individuals.add(
                    new Individual(
                            NearestNeighborInitializer
                                    .generate(instance)
                    )
            );
        }

        /*
            25% shuffled NN
         */
        int shuffledCount =
                parameters.populationSize / 4;

        for (int i = 0;
             i < shuffledCount;
             i++) {

            int[] route =
                    NearestNeighborInitializer
                            .generate(instance);

            for (int j = 0;
                 j < 5;
                 j++) {

                int a =
                        random.nextInt(n);

                int b =
                        random.nextInt(n);

                int temp =
                        route[a];

                route[a] =
                        route[b];

                route[b] =
                        temp;
            }

            individuals.add(
                    new Individual(route)
            );
        }

        /*
            random
         */
        while (individuals.size()
                < parameters.populationSize) {

            individuals.add(
                    new Individual(
                            randomPermutation(n)
                    )
            );
        }

        return new Population(individuals);
    }

    private int[] randomPermutation(
            int n) {

        List<Integer> values =
                new ArrayList<>();

        for (int i = 0;
             i < n;
             i++) {

            values.add(i);
        }

        Collections.shuffle(values);

        int[] permutation =
                new int[n];

        for (int i = 0;
             i < n;
             i++) {

            permutation[i] =
                    values.get(i);
        }

        return permutation;
    }

    private void evaluatePopulation(
            Population population,
            TSPInstance instance) {

        for (Individual individual
                : population.getIndividuals()) {

            individual.getDistance(instance);
        }
    }

    private boolean same(
            Individual a,
            Individual b) {

        for (int i = 0;
             i < a.size();
             i++) {

            if (a.getGene(i)
                    != b.getGene(i)) {

                return false;
            }
        }

        return true;
    }
}