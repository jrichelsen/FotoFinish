package fotofinish;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class FXMLDocumentController implements Initializable {

    private FotoFinishModel model;
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
    private NumberFieldFX brushSizeTextField;
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
    @FXML
    private ImageView imageViewer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.model = new FotoFinishModel();

        //TODO: make call function only when value changes by certain threshold
        this.brightnessSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            System.out.println("TODO: brightness changed to " + newValue);
        });

        this.contrastSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            System.out.println("TODO: contrast changed to " + newValue);
        });
    }

    @FXML
    private void filterGrayscale(ActionEvent event) {
        System.out.println("TODO: grayscale filter applied");
        model.applyGrayscaleFilter();
        this.refreshImageViewer();
    }

    @FXML
    private void filterSepia(ActionEvent event) {
        System.out.println("TODO: sepia filter applied");
        model.applySepiaFilter();
        this.refreshImageViewer();
    }

    @FXML
    private void filterInstant(ActionEvent event) {
        System.out.println("TODO: instant filter applied");
        model.applyInstantFilter();
        this.refreshImageViewer();
    }

    @FXML
    private void filterCustom(ActionEvent event) {
        System.out.println("TODO: launched custom filter popup");
        //TODO: code to create custom filter popup and get arguments for custom filter
        model.applyCustomFilter();
        this.refreshImageViewer();
    }

    @FXML
    private void filterNone(ActionEvent event) {
        System.out.println("TODO: none filter applied");
        model.reloadImage();
        this.refreshImageViewer();
    }

    @FXML
    private void brushTypeCircle(ActionEvent event) {
        System.out.println("TODO: brush type changed to circle");
    }

    @FXML
    private void brushTypeSquare(ActionEvent event) {
        System.out.println("TODO: brush type changed to square");
    }

    @FXML
    private void brushTypeSpraypaint(ActionEvent event) {
        System.out.println("TODO: brush type changed to spraypaint");
    }

    @FXML
    private void helpDoc(ActionEvent event) {
        System.out.println("TODO: launched help document");
    }

    @FXML
    private void aboutDialog(ActionEvent event) {
        System.out.println("TODO: created about dialog");
    }

    @FXML
    private void brushColor(ActionEvent event) {
        System.out.println("TODO: brush color changed to <BRUSH COLOR>");
    }

    @FXML
    private void brushSize(ActionEvent event) {
        System.out.println("TODO: brush size changed to <BRUSH SIZE>");
    }

    @FXML
    private void open(ActionEvent event) {
        System.out.println("TODO: launched file picker");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(null); //TODO: needs value in order to block main window
        model.loadImage(selectedFile);
        this.refreshImageViewer();
    }

    @FXML
    private void galleryButterfly(ActionEvent event) {
        System.out.println("TODO: opened butterfly file from gallery");
    }

    @FXML
    private void galleryTeddyBear(ActionEvent event) {
        System.out.println("TODO: opened teddy bear file from gallery");
    }

    @FXML
    private void galleryPrincess(ActionEvent event) {
        System.out.println("TODO: opened princess file from gallery");
    }

    @FXML
    private void galleryFirefighter(ActionEvent event) {
        System.out.println("TODO: opened firefighter file from gallery");
    }

    @FXML
    private void save(ActionEvent event) {
        System.out.println("TODO: saved file");
        model.saveImage();
    }

    @FXML
    private void saveAs(ActionEvent event) {
        System.out.println("TODO: launched file save as dialog");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.jpg"));
        File selectedFile = fileChooser.showSaveDialog(null); //TODO: needs value in order to block main window
        model.saveImageAs(selectedFile);
    }

    @FXML
    private void quit(ActionEvent event) {
        System.out.println("TODO: quit program");
    }

    @FXML
    private void newFile(ActionEvent event) {
        System.out.println("TODO: created new file");
    }

    private void refreshImageViewer() {
        this.imageViewer.setImage(model.getImage());
    }
}