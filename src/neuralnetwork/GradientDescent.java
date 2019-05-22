package neuralnetwork;

public class GradientDescent {
    private Structure structure;
    private double[] gradient;
    private double[][] newInputWeights;
    private double[][] newHiddenWeights;
    private double delta;
    private double s;
    private double lambda;

    public GradientDescent(Structure structure, double delta, double lambda) {
        this.structure = structure;
        this.newInputWeights = new double[structure.getHiddenWeights().length][structure.getHiddenWeights()[0].length];
        this.newHiddenWeights = new double[structure.getOutputWeights().length][structure.getOutputWeights()[0].length];
        this.gradient = new double[21];
        this.delta = delta;
        this.lambda = lambda;

    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public double getLambda() {
        return lambda;
    }

    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    public double[] getGradient() {
        return gradient;
    }

    public void setGradient() {
        //с весов вх. слоя
        double[][] tempWeights = structure.getHiddenWeights();
        double[][] tempOutWeights = structure.getOutputWeights();
        double newError;
        double oldError = structure.getErrorFunction();
        int counter = 0;
        for (int i = 0; i < tempWeights.length; i++) {
            for (int j = 0; j < tempWeights[0].length; j++) {
                tempWeights[i][j] = tempWeights[i][j] + delta;
                structure.setInputLayer();
                structure.setHiddenWeights(tempWeights);
                structure.setHiddenLayer();
                structure.setOutputWeights();
                structure.setT();
                structure.setErrorFunction();
                newError = structure.getErrorFunction();
                gradient[counter] = (newError - oldError) / delta;
                tempWeights[i][j] = tempWeights[i][j] - delta;
                counter++;
            }
        }
        for (int i = 0; i < tempOutWeights.length; i++) {
            for (int j = 0; j < tempOutWeights[0].length; j++) {
                oldError = structure.getErrorFunction();
                tempOutWeights[i][j] = tempOutWeights[i][j] + delta;
                structure.setInputLayer();
                structure.setHiddenWeights();
                structure.setHiddenLayer();
                structure.setOutputWeights(tempOutWeights);
                structure.setT();
                structure.setErrorFunction();
                newError = structure.getErrorFunction();
                gradient[counter] = (newError - oldError) / delta;
                tempOutWeights[i][j] = tempOutWeights[i][j] - delta;
                counter++;
            }
        }

    }

    public double getS() {
        return s;
    }

    public void setS() {
        for (int i = 0; i < gradient.length; i++) {
            s += Math.pow(gradient[i], 2);
        }
        this.s = Math.sqrt(s);
    }

    public double[][] getNewInputWeights() {
        return newInputWeights;
    }

    public void setNewInputWeights() {
        double[][] tempIW = structure.getHiddenWeights();
        int counter = 0;
        for (int i = 0; i < structure.getHiddenWeights().length; i++) {
            for (int j = 0; j < structure.getHiddenWeights()[0].length; j++) {
                tempIW[i][j] = tempIW[i][j] - (getGradient()[counter] / getS() * lambda);
                counter++;

            }
        }
        this.newInputWeights = tempIW;
    }

    public double[][] getNewHiddenWeights() {
        return newHiddenWeights;
    }

    public void setNewHiddenWeights() {
        int counter = 0;
        double[][] tempHW = structure.getOutputWeights();
        for (int i = 0; i < structure.getOutputWeights().length; i++) {
            for (int j = 0; j < structure.getOutputWeights()[0].length; j++) {
                tempHW[i][j] = tempHW[i][j] - (getGradient()[counter + 15] / getS() * lambda);
                counter++;
            }
        }

        this.newHiddenWeights = tempHW;
    }

}
