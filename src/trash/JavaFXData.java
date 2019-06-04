package trash;

public class JavaFXData {
    private String method;

    private int inputSize;

    private double delta;

    private double coefLearn;

    private double errorFunc;

    private double iteration;


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getInputSize() {
        return inputSize;
    }

    public void setInputSize(int inputSize) {
        this.inputSize = inputSize;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public double getCoefLearn() {
        return coefLearn;
    }

    public void setCoefLearn(double coefLearn) {
        this.coefLearn = coefLearn;
    }

    public double getErrorFunc() {
        return errorFunc;
    }

    public void setErrorFunc(double errorFunc) {
        this.errorFunc = errorFunc;
    }

    public double getIteration() {
        return iteration;
    }

    public void setIteration(double iteration) {
        this.iteration = iteration;
    }
}
