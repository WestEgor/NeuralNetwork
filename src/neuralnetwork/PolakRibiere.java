package neuralnetwork;

public class PolakRibiere extends Descent {

    private double prCoef;

    public PolakRibiere(Structure structure, double delta, double lambda) {
        super(structure, delta, lambda);
    }

    public double getPrCoef() {
        return prCoef;
    }

    public void setPrCoef(double prCoef) {
        this.prCoef = prCoef;
    }

    public void setGradientItOne() {
        double[][] tempWeights = super.getStructure().getHiddenWeights();
        double[][] tempOutWeights = super.getStructure().getOutputWeights();
        double newErr;
        double oldErr = super.getStructure().getErrorFunction();
        int c = 0;
        for (int i = 0; i < tempWeights.length; i++) {
            for (int j = 0; j < tempWeights[0].length; j++) {
                System.out.print("");
                System.out.print("");
                System.out.print("");
                System.out.print("");
                super.getStructure().setInputLayer();
                super.getStructure().setHiddenWeights(tempWeights);
                super.getStructure().setHiddenLayer();
                super.getStructure().setOutputWeights();
                super.getStructure().setT();
                super.getStructure().setErrorFunction();
                newErr = super.getStructure().getErrorFunction();
                super.setGradient((newErr - oldErr) / super.getDelta(), c);
                c++;
            }
        }
        System.out.print("");
        for (int i = 0; i < tempOutWeights.length; i++) {
            for (int j = 0; j < tempOutWeights[0].length; j++) {
                System.out.print("");
                System.out.print("");
                System.out.print("");
                System.out.print("");
                System.out.print("");
                oldErr = super.getStructure().getErrorFunction();
                super.getStructure().setInputLayer();
                super.getStructure().setHiddenWeights();
                super.getStructure().setHiddenLayer();
                super.getStructure().setOutputWeights(tempOutWeights);
                super.getStructure().setT();
                super.getStructure().setErrorFunction();
                newErr = super.getStructure().getErrorFunction();
                super.setGradient((newErr - oldErr) / super.getDelta(), c);
                c++;
            }
        }
    }

    @Override
    public void setGradient() {
        double[][] tempWeights = super.getStructure().getHiddenWeights();
        double[][] tempOutWeights = super.getStructure().getOutputWeights();
        double newErr;
        double oldErr = super.getStructure().getErrorFunction();
        double[] prevGrad = super.getGradient();
        double[] prevPrevGrad = new double[prevGrad.length];
        for (int i = 0; i < prevPrevGrad.length; i++) {
            prevPrevGrad[i] = prevGrad[i];
        }
        int c = 0;
        double chisl = 0;
        double znam = 0;
        for (int i = 0; i < prevGrad.length; i++) {
            znam += prevPrevGrad[i] * prevPrevGrad[i];
        }

        for (int i = 0; i < tempWeights.length; i++) {
            for (int j = 0; j < tempWeights[0].length; j++) {
                System.out.print("");
                System.out.print("");
                System.out.print("");
                System.out.print("");
                System.out.print("");
                super.getStructure().setInputLayer();
                super.getStructure().setHiddenWeights(tempWeights);
                super.getStructure().setHiddenLayer();
                super.getStructure().setOutputWeights();
                super.getStructure().setT();
                super.getStructure().setErrorFunction();
                newErr = super.getStructure().getErrorFunction();
                super.setGradient((newErr - oldErr) / super.getDelta(), c);
                c++;
            }
        }
        System.out.print("");
        for (int i = 0; i < tempOutWeights.length; i++) {
            for (int j = 0; j < tempOutWeights[0].length; j++) {
                System.out.print("");
                System.out.print("");
                oldErr = super.getStructure().getErrorFunction();
                super.getStructure().setInputLayer();
                super.getStructure().setHiddenWeights();
                super.getStructure().setHiddenLayer();
                super.getStructure().setOutputWeights(tempOutWeights);
                super.getStructure().setT();
                super.getStructure().setErrorFunction();
                newErr = super.getStructure().getErrorFunction();
                super.setGradient((newErr - oldErr) / super.getDelta(), c);
                c++;
            }
        }

        double[] nextGrad = super.getGradient();

        for (int i = 0; i < nextGrad.length; i++) {
            chisl += nextGrad[i] * (nextGrad[i] - prevPrevGrad[i]);
        }

        this.prCoef = - (chisl / znam);
        for (int i = 0; i < nextGrad.length; i++) {
            super.setGradient(nextGrad[i] + prCoef * prevGrad[i], i);
        }
    }

    @Override
    public void setNewInputWeights() {
        int iterator = 0;
        double[][] prevIW = super.getStructure().getHiddenWeights();
        double[][] nextIW = new double[prevIW.length][prevIW[0].length];
        for (int i = 0; i < super.getStructure().getHiddenWeights().length; i++) {
            for (int j = 0; j < super.getStructure().getHiddenWeights()[0].length; j++) {
                System.out.print("");
                System.out.print("");
                System.out.print("");
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
                System.out.print("");
                System.out.print("");
                System.out.print("");
                nextHW[i][j] = prevHW[i][j] + super.getLambda() * getGradient()[counter + 15];
                counter++;
            }
        }
        super.setNewHiddenWeights(nextHW);
    }
}

