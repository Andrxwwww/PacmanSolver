package pacman;

import java.util.Random;
import utils.*;

public class PacmanGeneticAlgorithm {

    private static final int POPULATION_SIZE = 20;
    private static final double MUTATION_RATE = 0.3;
    private static final int NUM_GENERATIONS = 800;
    
    private NeuralNetwork[] population;
    private Random random;

    public PacmanGeneticAlgorithm() {
        this.random = new Random();
        this.population = new NeuralNetwork[POPULATION_SIZE];
        initializePopulation();
    }

    private void initializePopulation() {
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population[i] = new NeuralNetwork(Commons.PACMAN_STATE_SIZE, Commons.PACMAN_NUM_ACTIONS);
        }
    }

    public NeuralNetwork evolve(int seed) {
        for (int generation = 0; generation < NUM_GENERATIONS; generation++) {
            NeuralNetwork[] newPopulation = new NeuralNetwork[POPULATION_SIZE];
            System.out.println("Generation: " + generation);
    
            NeuralNetwork elite = getBestNetwork(seed);
            newPopulation[0] = elite;

            double[] weights = calculateWeights(seed);
    
            for (int i = 1; i < POPULATION_SIZE; i++) {
                NeuralNetwork parent1 = weightedRandomSelection(weights);
                NeuralNetwork parent2 = weightedRandomSelection(weights);
                NeuralNetwork offspring = crossover(parent1, parent2);
                mutate(offspring);
                newPopulation[i] = offspring;
            }
    
            population = newPopulation;
        }
    
        return getBestNetwork(seed);
    }

    private double[] calculateWeights(int seed) {
        double[] weights = new double[POPULATION_SIZE];
        
        double totalFitness = 0.0;
        for (NeuralNetwork network : population) {
            totalFitness += evaluateFitness(network, seed);
        }
        
        for (int i = 0; i < POPULATION_SIZE; i++) {
            weights[i] = evaluateFitness(population[i], seed) / totalFitness;
        }
        
        return weights;
    }

    private NeuralNetwork weightedRandomSelection(double[] weights) {
        double randomValue = random.nextDouble();
        double cumulativeWeight = 0.0;
        
        for (int i = 0; i < POPULATION_SIZE; i++) {
            cumulativeWeight += weights[i];
            if (randomValue <= cumulativeWeight) {
                return population[i];
            }
        }
        
        // nao convem chegar aqui
        return null;
    }

    private NeuralNetwork crossover(NeuralNetwork parent1, NeuralNetwork parent2) {
        NeuralNetwork offspring = new NeuralNetwork(Commons.PACMAN_STATE_SIZE, Commons.PACMAN_NUM_ACTIONS);
    
        int crossoverPoint1 = random.nextInt(Commons.PACMAN_STATE_SIZE);
        int crossoverPoint2 = random.nextInt(Commons.PACMAN_STATE_SIZE);
    
        for (int i = 0; i < Commons.PACMAN_STATE_SIZE; i++) {
            for (int j = 0; j < 16; j++) {
                if (i < crossoverPoint1 || i > crossoverPoint2) {
                    offspring.getWeightsInputHidden()[i][j] = parent1.getWeightsInputHidden()[i][j];
                } else {
                    offspring.getWeightsInputHidden()[i][j] = parent2.getWeightsInputHidden()[i][j];
                }
            }
        }
    
        crossoverPoint1 = random.nextInt(16);
        crossoverPoint2 = random.nextInt(16);
    
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < Commons.PACMAN_NUM_ACTIONS; j++) {
                if (i < crossoverPoint1 || i > crossoverPoint2) {
                    offspring.getWeightsHiddenOutput()[i][j] = parent1.getWeightsHiddenOutput()[i][j];
                } else {
                    offspring.getWeightsHiddenOutput()[i][j] = parent2.getWeightsHiddenOutput()[i][j];
                }
            }
        }
    
        return offspring;
    }
    

    private void mutate(NeuralNetwork network) {
        for (int i = 0; i < Commons.PACMAN_STATE_SIZE; i++) {
            for (int j = 0; j < 16; j++) {
                if (random.nextDouble() < MUTATION_RATE) {
                    network.getWeightsInputHidden()[i][j] += random.nextGaussian() * 0.1;
                }
            }
        }

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < Commons.PACMAN_NUM_ACTIONS; j++) {
                if (random.nextDouble() < MUTATION_RATE) {
                    network.getWeightsHiddenOutput()[i][j] += random.nextGaussian() * 0.1;
                }
            }
        }
    }

    private double evaluateFitness(NeuralNetwork network , int seed) {
        NNPacmanGameController nnGameController = new NNPacmanGameController();
        nnGameController.setNeuralNetwork(network);

        PacmanBoard pacmanBoard = new PacmanBoard(nnGameController, false, seed);
        pacmanBoard.runSimulation();

        return pacmanBoard.getFitness();
    }

    private NeuralNetwork getBestNetwork( int seed) {
        NeuralNetwork best = population[0];
        double bestFitness = Double.NEGATIVE_INFINITY;

        for (NeuralNetwork network : population) {
            double fitness = evaluateFitness(network , seed);

            if (fitness > bestFitness) {
                best = network;
                bestFitness = fitness;
            }
        }
        System.out.println("Best fitness: " + bestFitness);
        return best;
    }
}
