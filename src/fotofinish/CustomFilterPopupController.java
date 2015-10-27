package fotofinish;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;

public class CustomFilterPopupController implements Initializable {

    private FotoFinishMainController mainController;
    private FotoFinishModel model;

    @FXML
    private Slider redSlider;
    @FXML
    private Slider greenSlider;
    @FXML
    private Slider blueSlider;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.redSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if(model.changeRed(newValue.doubleValue())) {
                mainController.refreshImageViewer();
            }
        });
        this.blueSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (model.changeBlue(newValue.doubleValue())) {
                mainController.refreshImageViewer();
            }
        });
        this.greenSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (model.changeGreen(newValue.doubleValue())) {
                mainController.refreshImageViewer();
            }
        });
    }    

    public void setMainController(FotoFinishMainController mainController) {
        this.mainController = mainController;
    }

    public void setModel(FotoFinishModel model) {
        this.model = model;
    }
}
