package fotofinish;

import java.awt.event.KeyEvent;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

//TODO: Find a better way to have image be refreshed
public class FXMLDocumentController implements Initializable {

    private static final Logger logger = Logger.getLogger(fotofinish.FXMLDocumentController.class.getName());

    Stage stage;
    private FotoFinishModel model = new FotoFinishModel();

    @FXML
    private Slider brightnessSlider;
    @FXML
    private Slider contrastSlider;
    @FXML
    private Slider saturationSlider;
    @FXML
    private ToggleGroup brushTypeRadioGroup;
    @FXML
    private RadioButton brushTypeCircleRadioButton;
    @FXML
    private RadioButton brushTypeSpraypaintRadioButton;
    @FXML
    private RadioButton brushTypeSquareRadioButton;
    @FXML
    private ColorPicker brushColorPicker;
    @FXML
    private NumberFieldFX brushSizeNumberField;
    @FXML
    private ImageView imageViewer;
    @FXML
    private ImageView imageViewerAbout;
    @FXML
    private ScrollPane imageScrollPane;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO: WHAT IS BLOCK INCREMENT?
        this.brightnessSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if(model.changeBrightness(newValue.doubleValue())) {
                this.refreshImageViewer();
            }
        });

        //TODO: convert to dragged call
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
        //TODO: code to create custom filter popup and get arguments for custom filter
        logger.log(Level.INFO, "TODO: custom filter popup launched");
        model.applyCustomFilter();
        this.refreshImageViewer();
        this.resetSliders();
    }

    @FXML
    private void filterNone(ActionEvent ignored) {
        model.resetImageToOriginal();
        this.refreshImageViewer();
        this.resetSliders();
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
        logger.log(Level.INFO, "TODO: help document launched");
        model.OpenHelpDialog();
    }

    @FXML
    private void aboutDialog(ActionEvent ignored) throws IOException {
        logger.log(Level.INFO, "TODO: about dialog created");
        model.OpenAboutDialog();
    }

    @FXML
    private void brushColor(ActionEvent ignored) {
        model.changeBrushColor(this.brushColorPicker.getValue());
    }

    @FXML
    private void brushSize(ActionEvent ignored) {
        model.changeBrushSize(Integer.parseInt(this.brushSizeNumberField.getText()));
    }

    //TODO: make title setting more intelligent
    @FXML
    private void open(ActionEvent ignored) {
        logger.log(Level.INFO, "open file chooser launched");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png")); //TODO: add other types
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
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png")); //TODO: add other types
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
        logger.log(Level.INFO, "TODO: new file created");
    }

    private void refreshImageViewer() {
        this.imageViewer.setImage(model.getImage());
        logger.log(Level.INFO, "image refreshed");
    }

    //TODO: make this connect better with FXML default value
    private void resetSliders() {
        this.brightnessSlider.setValue(0);
        this.contrastSlider.setValue(0);
        this.saturationSlider.setValue(0);
        logger.log(Level.INFO, "brightness, contrast and saturation sliders reset");
    }

    public void setStage(Stage stageFromMain) {
        this.stage = stageFromMain;
    }

}