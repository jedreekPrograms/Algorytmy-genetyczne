package algorithms.genetic;

public class GAParameters {

    public int populationSize;

    public int generations;

    public double crossoverRate;

    public double mutationRate;

    public int tournamentSize;

    public int eliteSize;

    public int noImprovementLimit;

    public int candidateCount;

    public int localSearchIterations;

    //co ile generacji uruchamiamy mocniejszy local search
    public int intensificationInterval;

    //ile osobnikow ulepszamy
    public int intensificationCount;

    public double memeticProbability;

    //nowe osobniki, swieza krew populacji
    public int immigrantsCount;

    public static GAParameters forProblemSize(int n) {

        GAParameters p = new GAParameters();

        if (n <= 500) {

            p.populationSize = 100;

            p.generations = 1400;

            p.crossoverRate = 0.95;

            p.mutationRate = 0.12;

            p.tournamentSize = 5;

            p.eliteSize = 3;

            p.noImprovementLimit = 300;

            p.candidateCount = 40;

            p.localSearchIterations = 80;

            p.intensificationInterval = 5;

            p.intensificationCount = 8;

            p.memeticProbability = 0.45;

            p.immigrantsCount = 3;
        }

        else if (n <= 3000) {

            p.populationSize = 70;

            p.generations = 600;

            p.crossoverRate = 0.92;

            p.mutationRate = 0.10;

            p.tournamentSize = 4;

            p.eliteSize = 2;

            p.noImprovementLimit = 160;

            p.candidateCount = 35;

            p.localSearchIterations = 50;

            p.intensificationInterval = 5;

            p.intensificationCount = 5;

            p.memeticProbability = 0.30;

            p.immigrantsCount = 2;
        }

        else {

            p.populationSize = 55;

            p.generations = 420;

            p.crossoverRate = 0.93;

            p.mutationRate = 0.11;

            p.tournamentSize = 5;

            p.eliteSize = 3;

            p.noImprovementLimit = 110;

            p.candidateCount = 28;

            p.localSearchIterations = 35;

            p.intensificationInterval = 5;

            p.intensificationCount = 5;

            p.memeticProbability = 0.28;

            p.immigrantsCount = 2;
        }

        return p;
    }
}