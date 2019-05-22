package neuralnetwork;



import java.util.Random;

public class GDTester {

    private static int DATA_SIZE = 10;

    public static void main(String[] args) {
        Structure st = new Structure(2, DATA_SIZE);
        GradientDescent gd = new GradientDescent(st, 0.001, 0.009);
        st.setX();
        st.setY();
        st.setF(vector());
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
        System.out.println("ИНПУТ ВЕСА");
        ActionsMatrix.showMatrix(st.getHiddenWeights());
        st.setHiddenLayer();
        System.out.println("ХИДДЕН ЛАЙЕР: ");
        ActionsMatrix.showMatrix(st.getHiddenLayer());
        st.setOutputWeights();
        System.out.println("СКРЫТЫЕ ВЕСА");
        ActionsMatrix.showMatrix(st.getOutputWeights());
        st.setT();
        System.out.println("ВЫХОД СЕТИ: ");
        ActionsMatrix.showVector(st.getT());
        System.out.println("FUNC ERR: ");
        st.setErrorFunction();
        System.out.println(st.getErrorFunction());
        int counter = 0;
        while (counter < 10000) {
            if (st.getErrorFunction() > 0.0002) {
                System.out.println("ГРАДИЕНТЫ:");
                gd.setGradient();
                ActionsMatrix.showVector(gd.getGradient());
                gd.setS();
                System.out.println("НАПРАВЛЕНИЕ ВЕКТОРА ГРАДИЕНТА:");
                System.out.println(gd.getS());
                System.out.println("НОВЫЕ ВЕСА НА ВХОДНОМ СЛОЕ НА  " + counter + " ИТЕРАЦИИ");
                gd.setNewInputWeights();
                st.setHiddenWeights(gd.getNewInputWeights());
                st.setHiddenLayer();
                ActionsMatrix.showMatrix(st.getHiddenWeights());
                //ActionsMatrix.showMatrix(st.getHiddenLayer());
                System.out.println("НОВЫЕ ВЕСА НА СКРЫТОМ СЛОЕ НА  " + counter + " ИТЕРАЦИИ");
                gd.setNewHiddenWeights();
                st.setOutputWeights(gd.getNewHiddenWeights());
                ActionsMatrix.showMatrix(st.getOutputWeights());
                st.setT();
                System.out.println("ВЫХОД СЕТИ:[" + counter + "]");
                ActionsMatrix.showVector(st.getT());
                System.out.println("FUNC ERR: [" + counter + "]");
                st.setErrorFunction();
                System.out.println(st.getErrorFunction());
                counter++;
            } else{
                System.out.println("СОШЛОСЬ");
                System.out.println("Эталонный выход");
                ActionsMatrix.showVector(st.getF());
                System.out.println("Полученные образцы");
                ActionsMatrix.showVector(st.getT());
                break;
            }
        }

    }

    private static double[] vector(){
        double[] newVector = new double[DATA_SIZE];
        Random rnd = new Random();
        for (int i = 0; i < newVector.length ; i++) {
            newVector[i] = Math.random()*0.049+0.49999999;
        }
        return newVector;
    }
}
