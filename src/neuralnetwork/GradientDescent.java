package neuralnetwork;

public class GradientDescent extends Descent {

    public GradientDescent(Structure structure, double delta, double lambda) {
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
                tempWeights[i][j] = tempWeights[i][j] + super.getDelta();
                super.getStructure().setInputLayer();
                super.getStructure().setHiddenWeights(tempWeights);
                super.getStructure().setHiddenLayer();
                super.getStructure().setOutputWeights();
                super.getStructure().setT();
                super.getStructure().setErrorFunction();
                newError = super.getStructure().getErrorFunction();
                super.setGradient((newError - oldError) / super.getDelta(), counter);
                tempWeights[i][j] = tempWeights[i][j] - super.getDelta();
                counter++;
            }
        }
        for (int i = 0; i < tempOutWeights.length; i++) {
            for (int j = 0; j < tempOutWeights[0].length; j++) {
                oldError = super.getStructure().getErrorFunction();
                tempOutWeights[i][j] = tempOutWeights[i][j] + super.getDelta();
                super.getStructure().setInputLayer();
                super.getStructure().setHiddenWeights();
                super.getStructure().setHiddenLayer();
                super.getStructure().setOutputWeights(tempOutWeights);
                super.getStructure().setT();
                super.getStructure().setErrorFunction();
                newError = super.getStructure().getErrorFunction();
                super.setGradient((newError - oldError) / super.getDelta(), counter);
                tempOutWeights[i][j] = tempOutWeights[i][j] - super.getDelta();
                counter++;
            }
        }
    }

    @Override
    public void setNewHiddenWeights() {
        int counter = 0;
        double[][] tempHW = super.getStructure().getOutputWeights();
        for (int i = 0; i < super.getStructure().getOutputWeights().length; i++) {
            for (int j = 0; j < super.getStructure().getOutputWeights()[0].length; j++) {
                tempHW[i][j] = tempHW[i][j] - (getGradient()[counter + 15] / getS() * super.getLambda());
                counter++;
            }
        }

        super.setNewHiddenWeights(tempHW);
    }

    @Override
    public void setNewInputWeights() {
        double[][] tempIW = super.getStructure().getHiddenWeights();
        int counter = 0;
        for (int i = 0; i < super.getStructure().getHiddenWeights().length; i++) {
            for (int j = 0; j < super.getStructure().getHiddenWeights()[0].length; j++) {
                tempIW[i][j] = tempIW[i][j] - (getGradient()[counter] / getS() * super.getLambda());
                counter++;

            }
        }
        super.setNewInputWeights(tempIW);
    }
}
