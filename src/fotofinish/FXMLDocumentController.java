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
import javafx.event.Event;
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
    @FXML
    private MenuItem menubarFileNew;
    @FXML
    private MenuItem menubarFileOpen;
    @FXML
    private MenuItem menubarFileGalleryButterfly;
    @FXML
    private MenuItem menubarFileGalleryTeddyBear;
    @FXML
    private MenuItem menubarFileGalleryPrincess;
    @FXML
    private MenuItem menubarFileGalleryFirefighter;
    @FXML
    private MenuItem menubarFileSave;
    @FXML
    private MenuItem menubarFileSaveAs;
    @FXML
    private MenuItem menubarFileQuit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO: make call function only when value changes by certain threshold
        brightnessSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("TODO: brightness changed to " + newValue);
            }
        });

        contrastSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("TODO: contrast changed to " + newValue);
            }
        });
    }

    @FXML
    private void applyFilterGrayscale(ActionEvent event) {
        System.out.println("TODO: grayscale filter applied");
    }

    @FXML
    private void applyFilterSepia(ActionEvent event) {
        System.out.println("TODO: sepia filter applied");
    }

    @FXML
    private void applyFilterInstant(ActionEvent event) {
        System.out.println("TODO: instant filter applied");
    }

    @FXML
    private void createFilterCustomPopup(ActionEvent event) {
        System.out.println("TODO: launched custom filter popup");
    }

    @FXML
    private void applyFilterNone(ActionEvent event) {
        System.out.println("TODO: none filter applied");
    }

    @FXML
    private void changeBrushTypeCircle(ActionEvent event) {
        System.out.println("TODO: brush type changed to circle");
    }

    @FXML
    private void changeBrushTypeSquare(ActionEvent event) {
        System.out.println("TODO: brush type changed to square");
    }

    @FXML
    private void changeBrushTypeSpraypaint(ActionEvent event) {
        System.out.println("TODO: brush type changed to spraypaint");
    }

    @FXML
    private void displayHelpDoc(ActionEvent event) {
        System.out.println("TODO: launched help document");
    }

    @FXML
    private void displayAboutDialog(ActionEvent event) {
        System.out.println("TODO: created about dialog");
    }

    @FXML
    private void changeBrushColor(ActionEvent event) {
        System.out.println("TODO: brush color changed to <BRUSH COLOR>");
    }

    @FXML
    private void adjustBrushSize(ActionEvent event) {
        System.out.println("TODO: brush size changed to <BRUSH SIZE>");
    }

    @FXML
    private void openFile(ActionEvent event) {
        System.out.println("TODO: launched file picker");
    }

    @FXML
    private void openFileGalleryButterfly(ActionEvent event) {
        System.out.println("TODO: opened butterfly file from gallery");
    }

    @FXML
    private void openFileGalleryTeddyBear(ActionEvent event) {
        System.out.println("TODO: opened teddy bear file from gallery");
    }

    @FXML
    private void openFileGalleryPrincess(ActionEvent event) {
        System.out.println("TODO: opened princess file from gallery");
    }

    @FXML
    private void openFileGalleryFirefighter(ActionEvent event) {
        System.out.println("TODO: opened firefighter file from gallery");
    }

    @FXML
    private void saveFile(ActionEvent event) {
        System.out.println("TODO: saved file");
    }


    @FXML
    private void saveFileAs(ActionEvent event) {
        System.out.println("TODO: launched file save as dialog");
    }


    @FXML
    private void quit(ActionEvent event) {
        System.out.println("TODO: quit program");
    }

    @FXML
    private void createNewFile(ActionEvent event) {
        System.out.println("TODO: created new file");
    }
}