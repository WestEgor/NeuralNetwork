package neuralnetwork;

public class SteepestDescent extends Descent {

    public SteepestDescent(Structure structure, double delta, double lambda) {
        super(structure, delta, lambda);

    }

    @Override
    public void setGradient() {
        //с весов вх. слоя
        double[][] tempWeights = super.getStructure().getHiddenWeights();
        double[][] tempOutWeights = super.getStructure().getOutputWeights();
        double newError;
        double oldError = super.getStructure().getErrorFunction();
        int counter = 0;
        for (int i = 0; i < tempWeights.length; i++) {
            for (int j = 0; j < tempWeights[0].length; j++) {
                super.getStructure().setInputLayer();
                super.getStructure().setHiddenWeights(tempWeights);
                super.getStructure().setHiddenLayer();
                super.getStructure().setOutputWeights();
                super.getStructure().setT();
                super.getStructure().setErrorFunction();
                newError = super.getStructure().getErrorFunction();
                super.setGradient((newError - oldError) / super.getDelta(), counter);
                counter++;
            }
        }
        for (int i = 0; i < tempOutWeights.length; i++) {
            for (int j = 0; j < tempOutWeights[0].length; j++) {
                oldError = super.getStructure().getErrorFunction();
                super.getStructure().setInputLayer();
                super.getStructure().setHiddenWeights();
                super.getStructure().setHiddenLayer();
                super.getStructure().setOutputWeights(tempOutWeights);
                super.getStructure().setT();
                super.getStructure().setErrorFunction();
                newError = super.getStructure().getErrorFunction();
                super.setGradient((newError - oldError) / super.getDelta(), counter);
                counter++;
            }
        }
    }

    @Override
    public void setNewInputWeights() {
        int iterator = 0;
        double[][] prevIW = super.getStructure().getHiddenWeights();
        double[][] nextIW = new double[prevIW.length][prevIW[0].length];
        for (int i = 0; i < super.getStructure().getHiddenWeights().length; i++) {
            for (int j = 0; j < super.getStructure().getHiddenWeights()[0].length; j++) {
                nextIW[i][j] = prevIW[i][j] + super.getLambda() * getGradient()[iterator];
                iterator++;
            }
        }
        super.setNewInputWeights(nextIW);
    }

    @Override
    public void setNewHiddenWeights() {
        int counter = 0;
        double[][] prevHW = super.getStructure().getOutputWeights();
        double[][] nextHW = new double[prevHW.length][prevHW[0].length];
        for (int i = 0; i < super.getStructure().getOutputWeights().length; i++) {
            for (int j = 0; j < super.getStructure().getOutputWeights()[0].length; j++) {
                nextHW[i][j] = prevHW[i][j] + super.getLambda() * getGradient()[counter + 15];
                counter++;
            }
        }
        super.setNewHiddenWeights(nextHW);
    }


}
