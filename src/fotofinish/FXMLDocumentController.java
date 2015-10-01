/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fotofinish;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 *
 * @author user
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private Button filterGrayscaleButton;
    @FXML
    private Button filterSepiaButton;
    @FXML
    private Button filterInstantButton;
    @FXML
    private Button filterCustomButton;
    @FXML
    private Button filterNoneButton;
    @FXML
    private Slider brightnessSlider;
    @FXML
    private Slider contrastSlider;
    @FXML
    private ToggleGroup brushTypeRadioGroup;
    @FXML
    private RadioButton brushTypeCircleRadioButton;
    @FXML
    private RadioButton brushTypeSpraypaintRadioButton;
    @FXML
    private RadioButton brushTypeSquareRadioButton;
    @FXML
    private MenuItem menubarHelpFotoFinishHelpMenuItem;
    @FXML
    private MenuItem menubarHelpAboutMenuItem;
    @FXML
    private Label filtersLabel;
    @FXML
    private Label sliderLabel;
    @FXML
    private Label brightnessLabel;
    @FXML
    private Label contrastLabel;
    @FXML
    private Label drawingLabel;
    @FXML
    private ColorPicker brushColorPicker;
    @FXML
    private Label brushTypeLabel;
    @FXML
    private Label brushSizeLabel;
    @FXML
    private TextField brushSizeTextField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO: make 
        brightnessSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("brightness changed to " + newValue);
            }
        });
    }

    @FXML
    private void applyFilterGrayscale(ActionEvent event) {
        System.out.println("grayscale filter applied");
    }

    @FXML
    private void applyFilterSepia(ActionEvent event) {
    }

    @FXML
    private void applyFilterInstant(ActionEvent event) {
    }

    @FXML
    private void createFilterCustomPopup(ActionEvent event) {
    }

    @FXML
    private void applyFilterNone(ActionEvent event) {
    }

    @FXML
    private void changeBrushTypeCircle(ActionEvent event) {
    }

    @FXML
    private void changeBrushTypeSquare(ActionEvent event) {
    }

    @FXML
    private void changeBrushTypeSpraypaint(ActionEvent event) {
    }

    @FXML
    private void displayHelpDialog(ActionEvent event) {
    }

    @FXML
    private void displayAboutDialog(ActionEvent event) {
    }

    @FXML
    private void changeBrushColor(ActionEvent event) {
    }

    @FXML
    private void adjustBrushSize(ActionEvent event) {
    }
    
}
