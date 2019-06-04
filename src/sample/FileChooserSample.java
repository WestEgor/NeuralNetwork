package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import neuralnetwork.*;

import java.io.FileWriter;
import java.io.IOException;

public class FileChooserSample extends Application {

    private VBox inputsFileds = new VBox();

    private ObservableList<String> methodList = FXCollections.observableArrayList(
            "Метод градиентного спуска",
            "Метод наискорейшего спуска",
            "Метод сопряженных градиентов (формула Полака-Рибьера)",
            "Метод сопряженных градиентов (формула Флетчера-Ривза)"
    );
    ComboBox<String> langsComboBox = new ComboBox<>(methodList);

    private TextField inputSizeData = new TextField();
    private TextField constantDelta = new TextField();
    private TextField learningRate = new TextField();
    private TextField minimalError = new TextField();
    private TextField iterations = new TextField();

    private TextArea result = new TextArea();

    private int counter;

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group(), Color.ORANGE);
        stage.setTitle("NEURO YMI");
        stage.setResizable(false);
        scene.getStylesheets().add("sample/style.css");
        stage.setWidth(530);
        stage.setHeight(580);

        final Label label = new Label("Neuro YMI");
        AnchorPane.setLeftAnchor(label, 0.0);
        AnchorPane.setRightAnchor(label, 0.0);
        label.setAlignment(Pos.CENTER);
        label.setFont(new Font("Georgia", 20));
        label.setStyle("-fx-alignment: center;");

        result.setFont(new Font("Georgia",18));
        result.setMaxWidth(500);
        result.setMaxHeight(550);
        result.setStyle( "-fx-background-color: black" );

        langsComboBox.setValue("Метод обучения нейронной сети");
        langsComboBox.setItems(methodList);
        langsComboBox.getStyleClass().add("center-aligned");


        inputSizeData.setPromptText("КОЛИЧЕСТВО ВХОДНЫХ ДАННЫХ");
        inputSizeData.setMaxWidth(300);
        inputSizeData.setAlignment(Pos.CENTER);

        constantDelta.setMaxWidth(300);
        constantDelta.setPromptText("КОНСТАНТА (δ)");
        constantDelta.setAlignment(Pos.BASELINE_CENTER);

        learningRate.setMaxWidth(300);
        learningRate.setPromptText("КОЭФФИЦИЕНТ ОБУЧЕНИЯ (λ)");
        learningRate.setAlignment(Pos.CENTER);

        minimalError.setMaxWidth(300);
        minimalError.setPromptText("ДОПУСТИМАЯ ОШИБКА СЕТИ");
        minimalError.setAlignment(Pos.CENTER);

        iterations.setMaxWidth(300);
        iterations.setPromptText("КОЛИЧЕСТВО МАКСИМАЛЬНЫХ ИТЕРАЦИЙ");
        iterations.setAlignment(Pos.CENTER);

        final Button addButton = new Button("Обучить");
        addButton.setFont(new Font("Georgia",20));
        addButton.setAlignment(Pos.CENTER);
        addButton.setPrefHeight(30);
        addButton.setPrefWidth(400);
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                learn();
            }
        });


        inputsFileds.getChildren().addAll(langsComboBox, inputSizeData, constantDelta, learningRate, minimalError, iterations, addButton);
        inputsFileds.setSpacing(5);
        inputsFileds.setPadding(new Insets(20, 30, 20, 30));

        inputSizeData.addEventFilter(KeyEvent.KEY_TYPED , numericValidation(10));
        constantDelta.addEventFilter(KeyEvent.KEY_TYPED , numericValidation(10));
        learningRate.addEventFilter(KeyEvent.KEY_TYPED , numericValidation(10));
        minimalError.addEventFilter(KeyEvent.KEY_TYPED , numericValidation(10));
        iterations.addEventFilter(KeyEvent.KEY_TYPED , numericValidation(10));

        final VBox mainView = new VBox();
        mainView.setSpacing(5);
        mainView.setPadding(new Insets(10, 20, 20, 10));

        mainView.getChildren().addAll(label, inputsFileds, result);

        ((Group) scene.getRoot()).getChildren().addAll(mainView);

        stage.setScene(scene);
        stage.show();
    }

    public void learn() {
        boolean isLearn = false;
        boolean check = false;
        String methodName = langsComboBox.getValue();


        int inputSize = Integer.parseInt(inputSizeData.getText());

        double delta = Double.parseDouble(constantDelta.getText());
        double lambda = Double.parseDouble(learningRate.getText());
        double minErr = Double.parseDouble(minimalError.getText());
        int iter = Integer.parseInt(iterations.getText());
        if (methodName.equals("Метод градиентного спуска")) {
            Structure structure1 = new Structure(2, inputSize);
            check = runGradientDescent(structure1, delta, lambda, iter, minErr, isLearn);
            if (check) {
                result.setText("АЛГОРИТМ УСПЕШНО СОЙДЕН!\n" +
                        "Алгоритм : " + methodName + "\nКоличество потраченых итераций : " + getCounter() +
                        "\nКоличество входных данных : " + inputSize + "\nОшибка нейронной сети : " + structure1.getErrorFunction());

            } else result.setText("НЕ СОШЛОСЬ");
            structure1 = null;

        }
        if (methodName.equals("Метод наискорейшего спуска")) {
            Structure structure2 = new Structure(2, inputSize);
            check = runSteepestDescent(structure2, delta, lambda, iter, minErr, isLearn);
            if (check) {
                result.setText("АЛГОРИТМ УСПЕШНО СОЙДЕН!\n" +
                        "Алгоритм : " + methodName + "\nКоличество потраченых итераций : " + getCounter() +
                        "\nКоличество входных данных : " + inputSize + "\nОшибка нейронной сети : " + structure2.getErrorFunction());

            } else result.setText("НЕ СОШЛОСЬ");
            structure2 = null;
        }
        if (methodName.equals("Метод сопряженных градиентов (формула Полака-Рибьера)")) {
            Structure structure3 = new Structure(2, inputSize);
            check = runPolakRibiere(structure3, delta, lambda, iter, minErr, isLearn);
            if (check) {
                result.setText("АЛГОРИТМ УСПЕШНО СОЙДЕН!\n" +
                        "Алгоритм : " + methodName + "\nКоличество потраченых итераций : " + getCounter() +
                        "\nКоличество входных данных : " + inputSize + "\nОшибка нейронной сети : " + structure3.getErrorFunction());

            } else result.setText("НЕ СОШЛОСЬ");
            structure3 = null;
        }
        if (methodName.equals("Метод сопряженных градиентов (формула Флетчера-Ривза)")) {
            Structure structure4 = new Structure(2, inputSize);
            check = runFletcherReeves(structure4, delta, lambda, iter, minErr, isLearn);
            if (check) {
                result.setText("АЛГОРИТМ УСПЕШНО СОЙДЕН!\n" +
                        "Алгоритм : " + methodName + "\nКоличество потраченых итераций : " + getCounter() +
                        "\nКоличество входных данных : " + inputSize + "\nОшибка нейронной сети : " + structure4.getErrorFunction());

            } else result.setText("НЕ СОШЛОСЬ");
            structure4 = null;

        }
    }

    public boolean runGradientDescent(Structure st, double delta, double lambda, int maxIter, double minErr, boolean check) {
        check = false;
        Descent gd = new GradientDescent(st, delta, lambda);
        st.setX();
        st.setY();
        double[] tempVect = new double[st.getDataSize()];
        for (int i = 0; i < st.getDataSize(); i++) {
            tempVect[i] = Math.random() * 0.049 + 0.59999999;
        }
        st.setF(tempVect);
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
        while (counter < maxIter) {
            if (st.getErrorFunction() > minErr) {
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
            } else {
                check = true;
                System.out.println("СОШЛОСЬ");
                System.out.println("Эталонный выход");
                ActionsMatrix.showVector(st.getF());
                System.out.println("Полученные образцы");
                ActionsMatrix.showVector(st.getT());
                break;
            }
        }
        setCounter(counter);
        String path = "src/files/resultGradientDescent.txt";
        writeDataToFile(path, st, counter);
        return check;
    }

    public boolean runSteepestDescent(Structure st, double delta, double lambda, int maxIteration, double minimalError, boolean check) {
        check = false;
        Descent sd = new SteepestDescent(st, delta, lambda);
        st.setX();
        st.setY();
        double[] tempVect = new double[st.getDataSize()];
        for (int i = 0; i < st.getDataSize(); i++) {
            tempVect[i] = Math.random() * 0.049 + 0.29999999;
        }

        st.setF(tempVect);
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
        double prevError;
        double nextError;
        sd.setGradient();
        while (counter < maxIteration) {
            if (st.getErrorFunction() > minimalError) {
                prevError = st.getErrorFunction();
                System.out.println("ГРАДИЕНТЫ:");
                ActionsMatrix.showVector(sd.getGradient());
                System.out.println("НОВЫЕ ВЕСА НА ВХОДНОМ СЛОЕ НА  " + counter + " ИТЕРАЦИИ");
                sd.setNewInputWeights();
                st.setHiddenWeights(sd.getNewInputWeights());
                st.setHiddenLayer();
                ActionsMatrix.showMatrix(st.getHiddenWeights());
                System.out.println("НОВЫЕ ВЕСА НА СКРЫТОМ СЛОЕ НА  " + counter + " ИТЕРАЦИИ");
                sd.setNewHiddenWeights();
                st.setOutputWeights(sd.getNewHiddenWeights());
                ActionsMatrix.showMatrix(st.getOutputWeights());
                st.setT();
                System.out.println("ВЫХОД СЕТИ:[" + counter + "]");
                ActionsMatrix.showVector(st.getT());
                System.out.println("FUNC ERR: [" + counter + "]");
                st.setErrorFunction();
                nextError = st.getErrorFunction();
                System.out.println(st.getErrorFunction());
                if (nextError > prevError) {
                    sd.setGradient();
                }
                counter++;
            } else {
                check = true;
                System.out.println("СОШЛОСЬ ");
                System.out.println("Эталонный выход ");
                ActionsMatrix.showVector(st.getF());
                System.out.println("Полученные образцы ");
                ActionsMatrix.showVector(st.getT());
                System.out.println("ОШИБКА ");
                System.out.println(st.getErrorFunction());
                break;
            }
        }
        String path = "src/files/resultSteepestDescent.txt";
        writeDataToFile(path, st, counter);
        setCounter(counter);

        return check;
    }

    public boolean runFletcherReeves(Structure st, double delta, double lambda, int maxIteration, double minimalError, boolean check) {
        check = false;
        Descent fr = new FletcherReeves(st, delta, lambda);
        st.setX();
        st.setY();
        double[] tempVect = new double[st.getDataSize()];
        for (int i = 0; i < st.getDataSize(); i++) {
            tempVect[i] = Math.random() * 0.049 + 0.69999999;
        }
        st.setF(tempVect);
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
        while (counter < maxIteration) {
            if (st.getErrorFunction() > minimalError) {
                if (counter == 0) {
                    ((FletcherReeves) fr).setGradientItOne();
                    System.out.println("ГРАДИЕНТЫ:");
                    ActionsMatrix.showVector(fr.getGradient());
                    System.out.println("НОВЫЕ ВЕСА НА ВХОДНОМ СЛОЕ НА  " + counter + " ИТЕРАЦИИ");
                    fr.setNewInputWeights();
                    st.setHiddenWeights(fr.getNewInputWeights());
                    st.setHiddenLayer();
                    ActionsMatrix.showMatrix(st.getHiddenWeights());
                    System.out.println("НОВЫЕ ВЕСА НА СКРЫТОМ СЛОЕ НА  " + counter + " ИТЕРАЦИИ");
                    fr.setNewHiddenWeights();
                    st.setOutputWeights(fr.getNewHiddenWeights());
                    ActionsMatrix.showMatrix(st.getOutputWeights());
                    st.setT();
                    System.out.println("ВЫХОД СЕТИ:[" + counter + "]");
                    ActionsMatrix.showVector(st.getT());
                    System.out.println("FUNC ERR: [" + counter + "]");
                    st.setErrorFunction();
                    System.out.println(st.getErrorFunction());
                    counter++;
                } else {
                    fr.setGradient();
                    System.out.println("ГРАДИЕНТЫ:");
                    ActionsMatrix.showVector(fr.getGradient());
                    System.out.println("НОВЫЕ ВЕСА НА ВХОДНОМ СЛОЕ НА  " + counter + " ИТЕРАЦИИ");
                    fr.setNewInputWeights();
                    st.setHiddenWeights(fr.getNewInputWeights());
                    st.setHiddenLayer();
                    ActionsMatrix.showMatrix(st.getHiddenWeights());
                    System.out.println("НОВЫЕ ВЕСА НА СКРЫТОМ СЛОЕ НА  " + counter + " ИТЕРАЦИИ");
                    fr.setNewHiddenWeights();
                    st.setOutputWeights(fr.getNewHiddenWeights());
                    ActionsMatrix.showMatrix(st.getOutputWeights());
                    st.setT();
                    System.out.println("ВЫХОД СЕТИ:[" + counter + "]");
                    ActionsMatrix.showVector(st.getT());
                    System.out.println("FUNC ERR: [" + counter + "]");
                    st.setErrorFunction();
                    System.out.println(st.getErrorFunction());
                    counter++;
                }
            } else {
                check = true;
                System.out.println("СОШЛОСЬ  ");
                System.out.println("Эталонный выход ");
                ActionsMatrix.showVector(st.getF());
                System.out.println("Полученные образцы ");
                ActionsMatrix.showVector(st.getT());
                System.out.println("ОШИБКА ");
                System.out.println(st.getErrorFunction());
                break;
            }
        }
        String path = "src/files/resultFletcherReeves.txt";
        writeDataToFile(path, st, counter);
        setCounter(counter);

        return check;
    }

    public boolean runPolakRibiere(Structure st, double delta, double lambda, int maxIteration, double minimalError, boolean check) {
        check = false;
        Descent pr = new PolakRibiere(st, delta, lambda);
        st.setX();
        st.setY();
        double[] tempVect = new double[st.getDataSize()];
        for (int i = 0; i < st.getDataSize(); i++) {
            tempVect[i] = Math.random() * 0.049 + 0.49999999;
        }
        st.setF(tempVect);
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
        while (counter < maxIteration) {
            if (st.getErrorFunction() > minimalError) {
                if (counter == 0) {
                    ((PolakRibiere) pr).setGradientItOne();
                    System.out.println("ГРАДИЕНТЫ:");
                    ActionsMatrix.showVector(pr.getGradient());
                    System.out.println("НОВЫЕ ВЕСА НА ВХОДНОМ СЛОЕ НА  " + counter + " ИТЕРАЦИИ");
                    pr.setNewInputWeights();
                    st.setHiddenWeights(pr.getNewInputWeights());
                    st.setHiddenLayer();
                    ActionsMatrix.showMatrix(st.getHiddenWeights());
                    System.out.println("НОВЫЕ ВЕСА НА СКРЫТОМ СЛОЕ НА  " + counter + " ИТЕРАЦИИ");
                    pr.setNewHiddenWeights();
                    st.setOutputWeights(pr.getNewHiddenWeights());
                    ActionsMatrix.showMatrix(st.getOutputWeights());
                    st.setT();
                    System.out.println("ВЫХОД СЕТИ:[" + counter + "]");
                    ActionsMatrix.showVector(st.getT());
                    System.out.println("FUNC ERR: [" + counter + "]");
                    st.setErrorFunction();
                    System.out.println(st.getErrorFunction());
                    counter++;
                } else {
                    System.out.print("");
                    pr.setGradient();
                    System.out.println("ГРАДИЕНТЫ:");
                    ActionsMatrix.showVector(pr.getGradient());
                    System.out.println("НОВЫЕ ВЕСА НА ВХОДНОМ СЛОЕ НА  " + counter + " ИТЕРАЦИИ");
                    pr.setNewInputWeights();
                    st.setHiddenWeights(pr.getNewInputWeights());
                    st.setHiddenLayer();
                    ActionsMatrix.showMatrix(st.getHiddenWeights());
                    System.out.println("НОВЫЕ ВЕСА НА СКРЫТОМ СЛОЕ НА  " + counter + " ИТЕРАЦИИ");
                    pr.setNewHiddenWeights();
                    st.setOutputWeights(pr.getNewHiddenWeights());
                    ActionsMatrix.showMatrix(st.getOutputWeights());
                    st.setT();
                    System.out.println("ВЫХОД СЕТИ:[" + counter + "]");
                    ActionsMatrix.showVector(st.getT());
                    System.out.println("FUNC ERR: [" + counter + "]");
                    st.setErrorFunction();
                    System.out.println(st.getErrorFunction());
                    counter++;
                }
            } else {
                check = true;
                System.out.print("");
                System.out.println("СОШЛОСЬ  ");
                System.out.println("Эталонный выход ");
                ActionsMatrix.showVector(st.getF());
                System.out.println("Полученные образцы ");
                ActionsMatrix.showVector(st.getT());
                System.out.println("ОШИБКА ");
                System.out.println(st.getErrorFunction());
                break;
            }
        }
        String path = "src/files/resultPolakRibiere.txt";
        writeDataToFile(path, st, counter);
        setCounter(counter);
        return check;
    }

    public void writeDataToFile(String filePath, Structure st, int iteration) {
        try {
            FileWriter fw = new FileWriter(filePath);

            fw.write("Количество входных данных: ");
            fw.write(String.valueOf(st.getDataSize()));
            fw.write("\nВходные образцы Х:\n");
            for (int i = 0; i < st.getT().length; i++) {
                fw.write(String.valueOf(st.getX()[i]) + "\n");
            }

            fw.write("\nВходные образцы Y:\n");
            for (int i = 0; i < st.getT().length; i++) {
                fw.write(String.valueOf(st.getY()[i]) + "\n");
            }

            fw.write("\nВыходные образцы (эталон) F:\n");
            for (int i = 0; i < st.getT().length; i++) {
                fw.write(String.valueOf(st.getF()[i]) + "\n");
            }

            fw.write("\nНовые веса на входном слое:\n");
            for (int i = 0; i < st.getHiddenWeights().length; i++) {
                for (int j = 0; j < st.getHiddenWeights()[0].length; j++) {
                    fw.write(String.valueOf(st.getHiddenWeights()[i][j]) + "   ");
                }
                fw.write("\n");
            }

            fw.write("\nНовые веса на скрытом слое:\n");
            for (int i = 0; i < st.getOutputWeights().length; i++) {
                for (int j = 0; j < st.getOutputWeights()[0].length; j++) {
                    fw.write(String.valueOf(st.getOutputWeights()[i][j]) + "   ");
                }
                fw.write("\n");
            }

            fw.write("\nПолученный образцы:\n");
            for (int i = 0; i < st.getT().length; i++) {
                fw.write(String.valueOf(st.getT()[i]) + "\n");
            }
            fw.write("\nОшибка сети: ");
            fw.write(String.valueOf(st.getErrorFunction()));
            fw.write("\nКоличество итераций: ");
            fw.write(String.valueOf(iteration));
            System.out.println("ФАЙЛ УСПЕШНО ЗАПИСАН!");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ОШИБКА ЗАПИСИ!");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public EventHandler<KeyEvent> numericValidation(final Integer max_Lengh) {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                TextField txt_TextField = (TextField) e.getSource();
                if (txt_TextField.getText().length() >= max_Lengh) {
                    e.consume();
                }
                if(e.getCharacter().matches("[0-9.]")){
                    if(txt_TextField.getText().contains(".") && e.getCharacter().matches("[.]")){
                        e.consume();
                    }else if(txt_TextField.getText().length() == 0 && e.getCharacter().matches("[.]")){
                        e.consume();
                    }
                }else{
                    e.consume();
                }
            }
        };
    }
}