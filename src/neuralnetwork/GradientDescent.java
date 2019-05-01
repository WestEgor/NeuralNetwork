package neuralnetwork;

public class GradientDescent {
    private Structure structure;
    private double[] gradient;
    private double delta;
    private double s;


    public GradientDescent(Structure structure, double delta) {
        this.structure = structure;
        this.gradient = new double[structure.getHiddenWeights().length
                * structure.getHiddenWeights()[0].length
                + structure.getOutputWeights().length + 1];
        this.delta = delta;

    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public double[] getGradient() {
        return gradient;
    }

    public void setGradient() {
        //с весов вх. слоя
        double[][] tempWeights;
        double[][] tempOutWeights;
        double newError = 0;
        double oldError = structure.getErrorFunction();
        for (int k = 0; k < gradient.length - 6; k++) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 5; j++) {
                    tempWeights = structure.getHiddenWeights();
                    tempWeights[i][j] = tempWeights[i][j] + delta;
                    structure.setInputLayer();
                    structure.setHiddenWeights(tempWeights);
                    structure.setHiddenLayer();
                    structure.setOutputWeights();
                    System.out.println("ВеСА:");
                    ActionsMatrix.showMatrix(structure.getHiddenWeights());
                    structure.setT();
                    structure.setErrorFunction();
                    newError = structure.getErrorFunction();
                    gradient[k] = (newError - oldError) / delta;
                    tempWeights[i][j] = tempWeights[i][j] - delta;
                }
            }
        }
        for (int k = 0; k < gradient.length - (gradient.length - 6); k++) {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 1; j++) {
                    oldError = structure.getErrorFunction();
                    tempOutWeights = structure.getOutputWeights();
                    tempOutWeights[i][j] = tempOutWeights[i][j] + delta;
                    structure.setInputLayer();
                    structure.setHiddenWeights();
                    structure.setHiddenLayer();
                    structure.setOutputWeights(tempOutWeights);
                    structure.setT();
                    structure.setErrorFunction();
                    newError = structure.getErrorFunction();
                    gradient[k + gradient.length - 6] = (newError - oldError) / delta;
                    tempOutWeights[i][j] = tempOutWeights[i][j] - delta;
                }
            }
        }

    }

    public double getS() {
        return s;
    }

    public void setS() {
        for (int i = 0; i < gradient.length; i++) {
            s += Math.pow(gradient[i],2);
        }
        this.s = Math.sqrt(s);
    }
}
