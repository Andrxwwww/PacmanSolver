package utils;

import pacman.PacmanBoard;

public class NNPacmanGameController implements GameController{

    private NeuralNetwork neuralNetwork;

    public NNPacmanGameController() {
        int inputSize = Commons.PACMAN_STATE_SIZE;
        int outputSize = Commons.PACMAN_NUM_ACTIONS;

        this.neuralNetwork = new NeuralNetwork(inputSize, outputSize);
    }

    public NeuralNetwork getNeuralNetwork() {
        return neuralNetwork;
    }

    public void setNeuralNetwork(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }

    @Override
    public int nextMove(int[] currentState) {
        // o estado atual para um array de double para a rede neural
        double[] input = new double[currentState.length];
        for (int i = 0; i < currentState.length; i++) {
            input[i] = currentState[i];
        }
    
        // forward usando a rede neural
        double[] output = neuralNetwork.forward(input);
    
        // procurar pelo index mais alto
        int maxIndex = 0;
        double maxValue = output[0];
        for (int i = 1; i < output.length; i++) {
            if (output[i] > maxValue) {
                maxValue = output[i];
                maxIndex = i;
            }
        }
    
        // retornar a ação correspondente ao index mais alto
        int action;
        switch (maxIndex) {
            case 0:
                action = PacmanBoard.NONE;
                break;
            case 1:
                action = PacmanBoard.LEFT;
                break;
            case 2:
                action = PacmanBoard.RIGHT;
                break;
            case 3:
                action = PacmanBoard.UP;
                break;
            case 4:
                action = PacmanBoard.DOWN;
                break;
            default:
                action = PacmanBoard.NONE;
                break;
        }
        return action;
    }
}
