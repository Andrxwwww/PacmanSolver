package utils;

public class NeuralNetwork {

    private int inputSize;
    private int hiddenSize;
    private int outputSize;

    private double[][] weightsInputHidden;
    private double[][] weightsHiddenOutput;

    public NeuralNetwork(int inputSize,int outputSize) {
        this.inputSize = inputSize;
        this.hiddenSize = 16;
        this.outputSize = outputSize;

        weightsInputHidden = new double[inputSize][hiddenSize];
        weightsHiddenOutput = new double[hiddenSize][outputSize];

        initializeWeights(weightsInputHidden);
        initializeWeights(weightsHiddenOutput);
    }

    // Getters and Setters

    public int getInputSize() {
        return inputSize;
    }

    public void setInputSize(int inputSize) {
        this.inputSize = inputSize;
    }

    public int getHiddenSize() {
        return hiddenSize;
    }

    public void setHiddenSize(int hiddenSize) {
        this.hiddenSize = hiddenSize;
    }

    public int getOutputSize() {
        return outputSize;
    }

    public void setOutputSize(int outputSize) {
        this.outputSize = outputSize;
    }

    public double[][] getWeightsInputHidden() {
        return weightsInputHidden;
    }

    public void setWeightsInputHidden(double[][] weightsInputHidden) {
        this.weightsInputHidden = weightsInputHidden;
    }

    public double[][] getWeightsHiddenOutput() {
        return weightsHiddenOutput;
    }

    public void setWeightsHiddenOutput(double[][] weightsHiddenOutput) {
        this.weightsHiddenOutput = weightsHiddenOutput;
    }


    private void initializeWeights(double[][] weights) {
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                weights[i][j] = Math.random() * 0.1 - 0.05; 
            }
        }
    }

    public double[] forward(double[] input) {
        double[] hiddenLayerOutput = new double[hiddenSize];
        double[] output = new double[outputSize];

        // Calculate hidden layer output
        for (int j = 0; j < hiddenSize; j++) {
            double sum = 0;
            for (int i = 0; i < inputSize; i++) {
                sum += input[i] * weightsInputHidden[i][j];
            }
            hiddenLayerOutput[j] = sigmoid(sum);
        }

        // Calculate output layer output
        for (int j = 0; j < outputSize; j++) {
            double sum = 0;
            for (int i = 0; i < hiddenSize; i++) {
                sum += hiddenLayerOutput[i] * weightsHiddenOutput[i][j];
            }
            output[j] = sigmoid(sum);
        }

        return output;
    }

    private double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }
}
