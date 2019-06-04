package neuralnetwork;

public abstract class Descent {
    private Structure structure;
    private double[] gradient;
    private double[][] newInputWeights;
    private double[][] newHiddenWeights;
    private double delta;
    private double s;
    private double lambda;

    public Descent(Structure structure, double delta, double lambda) {
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

    public void setGradient(double number, int counter){
        this.gradient[counter] = number;
    }

    public void setNewInputWeights(double[][] newInputWeights) {
        this.newInputWeights = newInputWeights;
    }

    public void setNewHiddenWeights(double[][] newHiddenWeights) {
        this.newHiddenWeights = newHiddenWeights;
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


    public double[][] getNewHiddenWeights() {
        return newHiddenWeights;
    }

    public abstract void setGradient();

    public abstract void setNewInputWeights();

    public abstract void setNewHiddenWeights();

}
