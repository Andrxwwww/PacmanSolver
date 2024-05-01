package pacman;

import utils.*;

public class MainPacman {

    // 0x3442F8 , 478

    private static final int SEED = 0x3442F8;

    public static void main(String[] args) {
        PacmanGeneticAlgorithm geneticAlgorithm = new PacmanGeneticAlgorithm();
        NeuralNetwork bestNetwork = geneticAlgorithm.evolve(SEED);

        NNPacmanGameController nnGameController = new NNPacmanGameController();
        nnGameController.setNeuralNetwork(bestNetwork);

        Pacman pacman = new Pacman(nnGameController, true , SEED);
    }
    
}
