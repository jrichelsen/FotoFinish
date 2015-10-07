package fotofinish;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class FXMLDocumentController implements Initializable {

    private static final Logger logger = Logger.getLogger(fotofinish.FXMLDocumentController.class.getName());
    private Label label;
    public Stage stage;
    BufferedImage img,original;
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
    private NumberFieldFX brushSizeNumberField;
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
    public ImageView imageViewer;
    public File selectedFile;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.model = new FotoFinishModel();

        //TODO: make call function only when value changes by certain threshold
        this.brightnessSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            /*if(model.changeBrightness(newValue)) {
                this.refreshImageViewer();
            }*/
            System.out.println("TODO: brightness changed to " + newValue);
            float brilho = (newValue.floatValue()) ;
            if ( 0 >= brilho) {
                brilho = (100+brilho);
                brilho = brilho/100;
            }
            else brilho = 1+ brilho/100;
            System.out.println(brilho);
            RescaleOp op = new RescaleOp(brilho, 0, null);
            op.filter(original, img);
            imageViewer.setImage(SwingFXUtils.toFXImage(img, null));
        });

        this.contrastSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            System.out.println("TODO: contrast changed to " + newValue);
            if (model.changeContrast((int) newValue)) {
                this.refreshImageViewer();
            }
        });
    }

    @FXML
    private void filterGrayscale(ActionEvent ignored) {
        System.out.println("TODO: grayscale filter applied");
        imageViewer.setImage(model.applyGrayscaleFilter(img));
        /*model.applyGrayscaleFilter();
        this.refreshImageViewer();*/
    }

    @FXML
    private void filterSepia(ActionEvent ignored) {
        System.out.println("TODO: sepia filter applied");
        imageViewer = model.applySepiaFilter(imageViewer);
        /*model.applySepiaFilter();
        this.refreshImageViewer();*/
    }

    @FXML
    private void filterInstant(ActionEvent event) {
        System.out.println("TODO: instant filter applied");
        model.applyInstantFilter();
        this.refreshImageViewer();
    }

    @FXML
    private void filterCustom(ActionEvent ignored) {
        System.out.println("TODO: launched custom filter popup");
        //TODO: code to create custom filter popup and get arguments for custom filter
        model.applyCustomFilter();
        this.refreshImageViewer();
    }

    @FXML
    private void filterNone(ActionEvent ignored) {
        System.out.println("TODO: none filter applied");
        imageViewer.setImage(SwingFXUtils.toFXImage(original, null));
        // model.reloadImage();
        this.refreshImageViewer();
    }

    @FXML
    private void brushTypeCircle(ActionEvent ignored) {
        System.out.println("TODO: brush type changed to circle");
        model.setBrushTypeCircle();
    }

    @FXML
    private void brushTypeSquare(ActionEvent ignored) {
        System.out.println("TODO: brush type changed to square");
        model.setBrushTypeSquare();
    }

    @FXML
    private void brushTypeSpraypaint(ActionEvent ignored) {
        System.out.println("TODO: brush type changed to spraypaint");
        model.setBrushTypeSpraypaint();
    }

    @FXML
    private void helpDoc(ActionEvent ignored) {
         System.out.println("TODO: launched help document");
    }

    @FXML
    private void aboutDialog(ActionEvent ignored) {
        System.out.println("TODO: created about dialog");
    }

    @FXML
    private void brushColor(ActionEvent ignored) {
        System.out.println("TODO: brush color changed to <BRUSH COLOR>");
        model.changeBrushColor(this.brushColorPicker.getValue());
    }

    @FXML
    private void brushSize(ActionEvent ignored) {
        System.out.println("TODO: brush size changed to <BRUSH SIZE>");
        model.changeBrushSize(Integer.parseInt(this.brushSizeNumberField.getText()));
    }

    @FXML
    private void open(ActionEvent ignored) throws MalformedURLException, IOException {
        logger.log(Level.INFO, "open file chooser launched");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(null); //TODO: needs value in order to block main window
        if (selectedFile != null) {
            logger.log(Level.INFO, "file {0} choosen in open file chooser", selectedFile);
            img = ImageIO.read(selectedFile);
            original = ImageIO.read(selectedFile);
            imageViewer.setImage(SwingFXUtils.toFXImage(img, null));
            /* OLD IMAGE LOADING CODE:
            model.loadImage(selectedFile);
            this.refreshImageViewer();
            */
        } else {
            logger.log(Level.INFO, "no file selected in open file chooser");
        }
    }

    @FXML
    private void galleryButterfly(ActionEvent ignored) {
        model.loadGalleryButterflyImage();
        this.refreshImageViewer();
        // TODO: opened butterfly file from gallery"
    }

    @FXML
    private void galleryTeddyBear(ActionEvent ignored) {
        model.loadGalleryTeddyBearImage();
        this.refreshImageViewer();
        // TODO: opened teddy bear file from gallery
    }

    @FXML
    private void galleryPrincess(ActionEvent ignored) {
        model.loadGalleryPrincessImage();
        this.refreshImageViewer();
        // TODO: opened princess file from gallery
    }

    @FXML
    private void galleryFirefighter(ActionEvent ignored) {
        model.loadGalleryFirefighterImage();
        this.refreshImageViewer();
        // TODO: opened firefighter file from gallery
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
    private void newFile(ActionEvent event) {
        System.out.println("TODO: created new file");
    }

    private void refreshImageViewer() {
        logger.log(Level.INFO, "image refreshed");
        this.imageViewer.setImage(model.getImage());
    }
}