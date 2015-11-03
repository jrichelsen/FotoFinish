package fotofinish;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.input.ContextMenuEvent;

public class CustomFilterPopupController implements Initializable {

    private static final Logger logger = Logger.getLogger(fotofinish.FotoFinishMainController.class.getName());

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
                mainController.refreshImageView();
            }
        });
        this.blueSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (model.changeBlue(newValue.doubleValue())) {
                mainController.refreshImageView();
            }
        });
        this.greenSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (model.changeGreen(newValue.doubleValue())) {
                mainController.refreshImageView();
            }
        });
    }

    @FXML
    private void resetRedSlider(ContextMenuEvent event) {
        this.redSlider.setValue(0);
        logger.log(Level.INFO, "red slider reset");
    }

    @FXML
    private void resetGreenSlider(ContextMenuEvent event) {
        this.greenSlider.setValue(0);
        logger.log(Level.INFO, "green slider reset");
    }

    @FXML
    private void resetBlueSlider(ContextMenuEvent event) {
        this.blueSlider.setValue(0);
        logger.log(Level.INFO, "blue slider reset");
    }

    public void setMainController(FotoFinishMainController mainController) {
        this.mainController = mainController;
    }

    public void setModel(FotoFinishModel model) {
        this.model = model;
    }
}
