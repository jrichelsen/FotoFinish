package fotofinish;

import java.io.File;
import java.io.IOException;
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
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

//TODO: Fix Pixels numberfield
public class FotoFinishMainController implements Initializable {

    private static final Logger logger = Logger.getLogger(fotofinish.FotoFinishMainController.class.getName());

    Stage stage;
    private final FotoFinishModel model;

    @FXML
    private Slider brightnessSlider;
    @FXML
    private Slider contrastSlider;
    @FXML
    private Slider saturationSlider;
    @FXML
    private NumberFieldFX brushSizeNumberField;
    @FXML
    private ImageView imageViewer;
    @FXML
    private ToggleGroup brushTypeToggleGroup;
    @FXML
    private ColorPicker brushColorPicker;

    public FotoFinishMainController() {
        this.model = new FotoFinishModel();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.brightnessSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if(model.changeBrightness(newValue.doubleValue())) {
                this.refreshImageViewer();
            }
        });
        this.contrastSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (model.changeContrast(newValue.doubleValue())) {
                this.refreshImageViewer();
            }
        });
        this.saturationSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (model.changeSaturation(newValue.doubleValue())) {
                this.refreshImageViewer();
            }
        });
    }

    @FXML
    private void newFile(ActionEvent ignored) {
        logger.log(Level.INFO, "TODO: new file created");
    }

    //TODO: make title setting more intelligent
    @FXML
    private void open(ActionEvent ignored) {
        logger.log(Level.INFO, "open file chooser launched");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.bmp", "*.jpg", "*.png")); //TODO: add other types
        File selectedFile = fileChooser.showOpenDialog(null); //TODO: needs value in order to block main window
        if (selectedFile != null) {
            logger.log(Level.INFO, "file {0} choosen in open file chooser", selectedFile);
            model.loadImage(selectedFile);
            this.refreshImageViewer();
            this.stage.setTitle("Foto Finish - " + selectedFile);
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
        model.saveImage();
    }

    @FXML
    private void saveAs(ActionEvent ignored) {
        logger.log(Level.INFO, "save as file chooser launched");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.bmp", "*.jpg", "*.png"));
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
    private void helpDocument(ActionEvent ignored) {
        model.openHelpDocument();
    }

    @FXML
    private void aboutDialog(ActionEvent ignored) throws IOException {
        model.openAboutDialog();
        logger.log(Level.INFO, "custom filter popup launched");
        logger.log(Level.INFO, "help document launched");
        logger.log(Level.INFO, "about dialog created");
    }

    @FXML
    private void filterGrayscale(ActionEvent ignored) {
        model.applyGrayscaleFilter();
        this.refreshImageViewer();
        this.resetSliders();
    }

    @FXML
    private void filterSepia(ActionEvent ignored) {
        model.applySepiaFilter();
        this.refreshImageViewer();
        this.resetSliders();
    }

    @FXML
    private void filterInstant(ActionEvent ignored) {
        model.applyInstantFilter();
        this.refreshImageViewer();
        this.resetSliders();
    }

    @FXML
    private void filterCustom(ActionEvent ignored) {
        model.launchCustomFilterPopup(this, model);
    }

    @FXML
    private void filterNone(ActionEvent ignored) {
        model.resetImageToOriginal();
        this.refreshImageViewer();
        this.resetSliders();
    }

    @FXML
    private void resetBrightnessSlider() {
        this.brightnessSlider.setValue(0);
        logger.log(Level.INFO, "brightness slider reset");
    }

    @FXML
    private void resetContrastSlider() {
        this.contrastSlider.setValue(0);
        logger.log(Level.INFO, "contrast slider reset");
    }

    @FXML
    private void resetSaturationSlider() {
        this.saturationSlider.setValue(0);
        logger.log(Level.INFO, "saturation slider reset");
    }

    //TODO: make this connect better with FXML default value
    private void resetSliders() {
        this.resetBrightnessSlider();
        this.resetContrastSlider();
        this.resetSaturationSlider();
    }

    @FXML
    private void brushColor(ActionEvent ignored) {
        model.changeBrushColor(this.brushColorPicker.getValue());
    }

    private void brushTypeCircle(ActionEvent ignored) {
        model.setBrushTypeCircle();
    }

    private void brushTypeSquare(ActionEvent ignored) {
        model.setBrushTypeSquare();
    }

    private void brushTypeSpraypaint(ActionEvent ignored) {
        model.setBrushTypeSpraypaint();
    }

    @FXML
    private void brushSize(ActionEvent ignored) {
        model.changeBrushSize(Integer.parseInt(this.brushSizeNumberField.getText()));
    }

    public void refreshImageViewer() {
        this.imageViewer.setImage(model.getImage());
        logger.log(Level.INFO, "image refreshed");
    }

    public void setStage(Stage stageFromMain) {
        this.stage = stageFromMain;
    }
}