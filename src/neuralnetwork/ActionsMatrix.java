package neuralnetwork;

public class ActionsMatrix {

    public static double[][] multiplyMatrix(double[][] m1, double[][] m2) {
        int m1ColLength = m1[0].length; // m1 columns length
        int m2RowLength = m2.length;    // m2 rows length
        if (m1ColLength != m2RowLength) return null; // matrix multiplication is not possible
        int mRRowLength = m1.length;    // m result rows length
        int mRColLength = m2[0].length; // m result columns length
        double[][] mResult = new double[mRRowLength][mRColLength];
        for (int i = 0; i < mRRowLength; i++) {         // rows from m1
            for (int j = 0; j < mRColLength; j++) {     // columns from m2
                for (int k = 0; k < m1ColLength; k++) { // columns from m1
                    mResult[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return mResult;
    }

    public static double[] multiplyVector(double[] vector, double[][] matrix) {
        double newVector[] = new double[matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                newVector[j] += vector[i] * matrix[i][j];
            }
        }
        return newVector;
    }


    public static double[] matrixToVector(double[][] matrix) {
        double[] newVector = new double[matrix.length * matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            double[] row = matrix[i];
            for (int j = 0; j < matrix[0].length; j++) {
                double number = matrix[i][j];
                newVector[i * row.length + j] = number;
            }
        }
        return newVector;

    }


    public static double getMin(double[] vector) {
        double min = vector[0];
        for (int i = 0; i < vector.length; i++) {
            if (min > vector[i]) {
                min = vector[i];
            }
        }
        return min;
    }

    public static double getMax(double[] vector) {
        double max = vector[0];
        for (int i = 0; i < vector.length; i++) {
            if (max < vector[i]) {
                max = vector[i];
            }
        }
        return max;
    }




    public static void showVector(double[] vector) {
        for (int i = 0; i < vector.length; i++) {
            System.out.print(vector[i] + "  ");
        }
        System.out.println();
    }

    public static void showMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + "  ");
            }
            System.out.println();
        }
    }

}

