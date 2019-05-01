package neuralnetwork;

public class Tester {
    public static void main(String[] args) {

        Structure st = new Structure(2,5);
        st.setX();
        st.setY();
        st.setF();
        System.out.println("ВЕКТОР X:");
        ActionsMatrix.showVector(st.getX());
        System.out.println("ВЕКТОР Y:");
        ActionsMatrix.showVector(st.getY());
        System.out.println("ВЕКТОР F:");
        ActionsMatrix.showVector(st.getF());
        st.setInputLayer();
        System.out.println("ИНПУТ ЛАЙЕР: ");
        ActionsMatrix.showMatrix(st.getInputLayer());
        st.setHiddenWeights();
        st.setHiddenLayer();
        System.out.println("ХИДДЕН ЛАЙЕР: ");
        ActionsMatrix.showMatrix(st.getHiddenLayer());
        st.setOutputWeights();
        st.setT();
        System.out.println("ВЫХОД СЕТИ: ");
        ActionsMatrix.showVector(st.getT());
        System.out.println("FUNC ERR: ");
        st.setErrorFunction();
        System.out.println(st.getErrorFunction());
        System.out.println("))))):");
        GradientDescent gd = new GradientDescent(st,0.0001);
        gd.setGradient();
        ActionsMatrix.showVector(gd.getGradient());
        gd.setS();
        System.out.println(gd.getS());
    }
}
