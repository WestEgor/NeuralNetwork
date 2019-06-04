package trash;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import trash.JavaFXData;

public class Controller {

    private ObservableList<String> methodList = FXCollections.observableArrayList(
            "Метод градиентного спуска",
            "Метод наискорейшего спуска",
            "Метод сопряженных градиентов (формула Полака-Рибьера)",
            "Метод сопряженных градиентов (формула Флетчера-Ривза)"
    );

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button startNeuroLearning;

    @FXML
    private TextField inputDataSize;

    @FXML
    private TextField constantDelta;

    @FXML
    private ComboBox<String> methodLearning;

    @FXML
    private TextField learningRate;

    @FXML
    private TextField minimalError;

    @FXML
    private TextField maxIterations;

    private JavaFXData jData;

    @FXML
    void initialize() {
        jData = new JavaFXData();
        //Добавление списка
        methodLearning.setValue("Метод градиентного спуска");
        methodLearning.setItems(methodList);
        //Чтение данных из филдов
        startNeuroLearning.setOnAction(actionEvent -> {
            startNeuroLearning.getScene().getWindow().hide();
            jData.setMethod(methodLearning.getValue());
            jData.setInputSize(Integer.parseInt(inputDataSize.getText()));
            jData.setDelta(Double.parseDouble(constantDelta.getText()));
            jData.setCoefLearn(Double.parseDouble(learningRate.getText()));
            jData.setErrorFunc(Double.parseDouble(minimalError.getText()));
            jData.setIteration(Double.parseDouble(maxIterations.getText()));

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/trash/secondMenu.fxml"));
                Parent root1 =  fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();
            } catch(Exception e) {
                e.printStackTrace();
            }
        });

    }





}
