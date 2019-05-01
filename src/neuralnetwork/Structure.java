package neuralnetwork;

import static neuralnetwork.ActionsMatrix.getMax;
import static neuralnetwork.ActionsMatrix.getMin;

public class Structure {
    private int inputSize; //сколько вх. данных
    private int dataSize; // кол-во вх. данных
    private double[] x;
    private double[] y;
    private double[] f; //эталон вых. сети
    private double[][] inputLayer; // вх. слой
    private double[][] hiddenWeights;
    private double[][] outputWeights;
    private double[][] hiddenLayer;
    private double[] t; //выход сети
    private double errorFunction;


    public Structure(int inputSize, int dataSize) {
        this.inputSize = inputSize;
        this.dataSize = dataSize;
        this.x = new double[dataSize];
        this.y = new double[dataSize];
        this.f = new double[dataSize];
        this.inputLayer = new double[dataSize][inputSize + 1];
        this.hiddenWeights = new double[inputSize + 1][2 * inputSize + 1];
        this.hiddenLayer = new double[dataSize][(2 * inputSize + 1) + 1];
        this.outputWeights = new double[hiddenLayer[0].length][1];
        this.t = new double[dataSize];

    }

    public int getInputSize() {
        return inputSize;
    }

    public void setInputSize(int inputSize) {
        this.inputSize = inputSize;
    }

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public double[] getX() {
        return x;
    }

    public void setX() {
        for (int i = 0; i < x.length; i++) {
            x[i] = Math.random() * 2;
        }
        if (dataSize > 2) {
            //нормализация данных
            this.x = normalize(x);
        }
    }

    public double[] getY() {
        return y;
    }

    public void setY() {
        for (int i = 0; i < y.length; i++) {
            y[i] = Math.random() * 3;
        }
        if (dataSize > 2) {
            //нормализация данных
            this.y = normalize(y);
        }
    }

    public double[] getF() {
        return f;
    }

    public void setF() {
        for (int i = 0; i < f.length; i++) {
            f[i] = Math.random() * 17;
        }
        if (dataSize > 2) {
            //нормализация данных
            this.f = normalize(f);
        }
    }

    public double[][] getInputLayer() {
        return inputLayer;
    }

    public void setInputLayer() {
        for (int i = 0; i < inputLayer.length; i++) {
            for (int j = 0; j < inputLayer[0].length; j++) {
                if (j == 0) inputLayer[i][j] = 1;
                if (j == 1) inputLayer[i][j] = getX()[i];
                if (j == 2) inputLayer[i][j] = getY()[i];
            }
        }
    }

    public double[][] getHiddenWeights() {
        return hiddenWeights;
    }

    public void setHiddenWeights() {
        for (int i = 0; i < hiddenWeights.length; i++) {
            for (int j = 0; j < hiddenWeights[0].length; j++) {
                this.hiddenWeights[i][j] = Math.random() * 2 - 1;
            }
        }
    }

    public void setHiddenWeights(double[][] hiddenWeights) {
        this.hiddenWeights = hiddenWeights;
    }

    public double[][] getOutputWeights() {
        return outputWeights;
    }

    public void setOutputWeights() {
        for (int i = 0; i < outputWeights.length; i++) {
            for (int j = 0; j < outputWeights[0].length; j++) {
                this.outputWeights[i][j] = Math.random() * 2 - 1;
            }
        }
    }

    public void setOutputWeights(double[][] outputWeights) {
        this.outputWeights = outputWeights;
    }

    public double[][] getHiddenLayer() {
        return this.hiddenLayer;
    }

    public void setHiddenLayer() {
        for (int k = 0; k < dataSize; k++) {
            for (int i = 0; i < hiddenWeights.length; i++) {
                for (int j = 0; j < hiddenWeights[0].length; j++) {
                    if (j == 0) {
                        hiddenLayer[k][j] = 1;
                    }
                    hiddenLayer[k][j] += inputLayer[k][i] * hiddenWeights[i][j];
                }
            }
        }

        for (int i = 0; i < hiddenLayer.length; i++) {
            for (int j = 0; j < hiddenLayer[0].length; j++) {
                this.hiddenLayer[i][j] = activationFunc(hiddenLayer[i][j]);
            }
        }
    }


    public double[] getT() {
        return t;
    }

    public void setT() {
        for (int k = 0; k < dataSize; k++) {
            for (int i = 0; i < outputWeights.length; i++) {
                for (int j = 0; j < outputWeights[0].length; j++) {
                    this.t[k] += hiddenLayer[k][j] * outputWeights[i][j];
                }
            }
        }

        for (int i = 0; i < t.length; i++) {
            this.t[i] = activationFunc(t[i]);
        }
    }

    public double getErrorFunction() {
        return errorFunction;
    }

    public void setErrorFunction() {
        double tempError = 0;
        for (int i = 0; i < dataSize; i++) {
            tempError += Math.pow((t[i] - f[i]), 2);
        }
        this.errorFunction = tempError / dataSize;
    }

    private double[] normalize(double[] vector) {
        double[] normalizeVector = new double[vector.length];
        double max = getMax(vector);
        double min = getMin(vector);
        for (int i = 0; i < normalizeVector.length; i++) {
            normalizeVector[i] = (vector[i] - min) / (max - min);
        }
        return normalizeVector;
    }

    private double activationFunc(double number) {
        return 1 / (1 + Math.exp(-number));
    }


}

