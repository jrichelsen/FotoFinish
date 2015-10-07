package fotofinish;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class FXMLDocumentController implements Initializable {

    private static final Logger logger = Logger.getLogger(fotofinish.FXMLDocumentController.class.getName());
    private FotoFinishModel model;
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
    private ColorPicker brushColorPicker;
    @FXML
    private NumberFieldFX brushSizeNumberField;
    @FXML
    private ImageView imageViewer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.model = new FotoFinishModel();

        //TODO: make call function only when value changes by certain threshold
        this.brightnessSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if(model.changeBrightness(newValue)) {
                this.refreshImageViewer();
            }
        });

        this.contrastSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if(model.changeContrast(newValue)) {
                this.refreshImageViewer();
            }
        });
    }

    @FXML
    private void filterGrayscale(ActionEvent ignored) {
        model.applyGrayscaleFilter();
        this.refreshImageViewer();
    }

    @FXML
    private void filterSepia(ActionEvent ignored) {
        model.applySepiaFilter();
        this.refreshImageViewer();
    }

    @FXML
    private void filterInstant(ActionEvent ignored) {
        model.applyInstantFilter();
        this.refreshImageViewer();
    }

    @FXML
    private void filterCustom(ActionEvent ignored) {
        //TODO: code to create custom filter popup and get arguments for custom filter
        model.applyCustomFilter();
        this.refreshImageViewer();
    }

    @FXML
    private void filterNone(ActionEvent ignored) {
        model.reloadImage();
        this.refreshImageViewer();
    }

    @FXML
    private void brushTypeCircle(ActionEvent ignored) {
        model.setBrushTypeCircle();
    }

    @FXML
    private void brushTypeSquare(ActionEvent ignored) {
        model.setBrushTypeSquare();
    }

    @FXML
    private void brushTypeSpraypaint(ActionEvent ignored) {
        model.setBrushTypeSpraypaint();
    }

    @FXML
    private void helpDoc(ActionEvent ignored) {
    }

    @FXML
    private void aboutDialog(ActionEvent ignored) {
    }

    @FXML
    private void brushColor(ActionEvent ignored) {
        model.changeBrushColor(this.brushColorPicker.getValue());
    }

    @FXML
    private void brushSize(ActionEvent ignored) {
        model.changeBrushSize(Integer.parseInt(this.brushSizeNumberField.getText()));
    }

    @FXML
    private void open(ActionEvent ignored) {
        logger.log(Level.INFO, "open file chooser launched");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(null); //TODO: needs value in order to block main window
        if (selectedFile != null) {
            logger.log(Level.INFO, "file {0} choosen in open file chooser", selectedFile);
            model.loadImage(selectedFile);
            this.refreshImageViewer();
        } else {
            logger.log(Level.INFO, "no file selected in open file chooser");
        }
    }

    @FXML
    private void galleryButterfly(ActionEvent ignored) {
        model.loadGalleryButterflyImage();
        this.refreshImageViewer();
    }

    @FXML
    private void galleryTeddyBear(ActionEvent ignored) {
        model.loadGalleryTeddyBearImage();
        this.refreshImageViewer();
    }

    @FXML
    private void galleryPrincess(ActionEvent ignored) {
        model.loadGalleryPrincessImage();
        this.refreshImageViewer();
    }

    @FXML
    private void galleryFirefighter(ActionEvent ignored) {
        model.loadGalleryFirefighterImage();
        this.refreshImageViewer();
    }

    @FXML
    private void save(ActionEvent ignored) {
        System.out.println("TODO: saved file");
        model.saveImage();
    }

    @FXML
    private void saveAs(ActionEvent ignored) {
        logger.log(Level.INFO, "save as file chooser launched");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.jpg"));
        File selectedFile = fileChooser.showSaveDialog(null); //TODO: needs value in order to block main window
        if (selectedFile != null) {
            logger.log(Level.INFO, "file {0} choosen in save as file chooser", selectedFile);
            model.saveImageAs(selectedFile);
        } else {
            logger.log(Level.INFO, "no file selected in save as file chooser");
        }
    }

    @FXML
    private void quit(ActionEvent ignored) {
        logger.log(Level.INFO, "quitting");
        Platform.exit(); //TODO: make this detect unsaved changes
    }

    @FXML
    private void newFile(ActionEvent ignored) {
    }

    private void refreshImageViewer() {
        logger.log(Level.INFO, "image refreshed");
        this.imageViewer.setImage(model.getImage());
    }
}