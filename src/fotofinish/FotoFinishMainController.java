package fotofinish;

import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import static org.junit.Assert.*;

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
    private Slider gaussianBlurSlider;
    @FXML
    private NumberFieldFX brushSizeNumberField;
    @FXML
    private ToggleGroup brushTypeToggleGroup;
    @FXML
    private ColorPicker brushColorPicker;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ImageView imageView;

    private final DoubleProperty zoom = new SimpleDoubleProperty(200);

    public FotoFinishMainController() {
        this.model = new FotoFinishModel();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.brightnessSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if(model.changeBrightness(newValue.doubleValue())) {
                this.refreshImageView();
            }
        });
        this.contrastSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (model.changeContrast(newValue.doubleValue())) {
                this.refreshImageView();
            }
        });
        this.saturationSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (model.changeSaturation(newValue.doubleValue())) {
                this.refreshImageView();
            }
        });
        this.saturationSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (model.changeSaturation(newValue.doubleValue())) {
                this.refreshImageView();
            }
        });
        this.gaussianBlurSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            model.applyGaussianBlur(newValue.doubleValue());
            this.refreshImageView();
        });

        this.imageView.preserveRatioProperty().set(true);
        this.zoom.addListener((Observable arg0) -> {
            this.imageView.setFitWidth(this.zoom.get() * 3);
            this.imageView.setFitHeight(this.zoom.get() * 3);
        });
        this.scrollPane.addEventFilter(ScrollEvent.ANY, (ScrollEvent event) -> {
            if (event.getDeltaY() > 0) {
                this.zoom.set(this.zoom.get() * 1.1);
            } else if (event.getDeltaY() < 0) {
                this.zoom.set(this.zoom.get() / 1.1);
            }
        });

        // regression tests
        this.testChangeBrightnessMin();
        this.testChangeBrightnessMax();
        this.testGetImage();
        this.testResetImageToOriginal();
        this.testSaveImageAs();

        // gaussian blur tests
        this.testSliderCallsFunction();
        //this.testPixelsChanged();
        //this.testCorrectPixels();
        this.testBlurOutput();
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
            this.refreshImageView();
            this.stage.setTitle("Foto Finish - " + selectedFile);
        } else {
            logger.log(Level.INFO, "no file selected in open file chooser");
        }
    }

    @FXML
    private void galleryButterfly(ActionEvent ignored) {
        model.loadGalleryButterflyImage();
        this.refreshImageView();
    }

    @FXML
    private void galleryTeddyBear(ActionEvent ignored) {
        model.loadGalleryTeddyBearImage();
        this.refreshImageView();
    }

    @FXML
    private void galleryPrincess(ActionEvent ignored) {
        model.loadGalleryPrincessImage();
        this.refreshImageView();
    }

    @FXML
    private void galleryFirefighter(ActionEvent ignored) {
        model.loadGalleryFirefighterImage();
        this.refreshImageView();
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
    }

    @FXML
    private void filterGrayscale(ActionEvent ignored) {
        model.applyGrayscaleFilter();
        this.refreshImageView();
        this.resetSliders();
    }

    @FXML
    private void filterSepia(ActionEvent ignored) {
        model.applySepiaFilter();
        this.refreshImageView();
        this.resetSliders();
    }

    @FXML
    private void filterInstant(ActionEvent ignored) {
        model.applyInstantFilter();
        this.refreshImageView();
        this.resetSliders();
    }

    @FXML
    private void filterCustom(ActionEvent ignored) {
        model.launchCustomFilterPopup(this, model);
    }

    @FXML
    private void filterNone(ActionEvent ignored) {
        model.resetImageToOriginal();
        this.refreshImageView();
        this.resetSliders();
    }

    //TODO: make this connect better with FXML default value
    private void resetSliders() {
        this.resetBrightnessSlider();
        this.resetContrastSlider();
        this.resetSaturationSlider();
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

    public void refreshImageView() {
        this.imageView.setImage(model.getImage());
        logger.log(Level.INFO, "image refreshed");
    }

    public void setStage(Stage stageFromMain) {
        this.stage = stageFromMain;
    }

    private boolean isOneColor(Image img, Color desiredColor) {
        PixelReader pixRead = img.getPixelReader();
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color pixelColor = pixRead.getColor(x, y);
                if (!pixelColor.equals(desiredColor)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void testChangeBrightnessMin() {
        System.out.println("changeBrightness to min");
        double newBrightness = -1;
        FotoFinishModel testModel = new FotoFinishModel();

        testModel.loadGalleryButterflyImage();
        testModel.changeBrightness(newBrightness);
        assertTrue(this.isOneColor(testModel.getImage(), Color.BLACK));
    }

    public void testChangeBrightnessMax() {
        System.out.println("changeBrightness to max");
        double newBrightness = 1;
        FotoFinishModel testModel = new FotoFinishModel();

        testModel.loadGalleryButterflyImage();
        testModel.changeBrightness(newBrightness);
        assertTrue(this.isOneColor(testModel.getImage(), Color.WHITE));
    }

    public void testGetImage() {
        System.out.println("getImage test");
        FotoFinishModel testModel = new FotoFinishModel();

        testModel.loadGalleryButterflyImage();
        assertNotNull(testModel.getImage());
    }

    public void testResetImageToOriginal() {
        System.out.println("resetImageToOriginal test");
        double newBrightness = 1;
        FotoFinishModel testModel = new FotoFinishModel();

        testModel.loadGalleryButterflyImage();
        Image initialImage = testModel.getImage();

        testModel.changeBrightness(newBrightness);

        testModel.resetImageToOriginal();
        Image resetImage = testModel.getImage();
        assertEquals(initialImage, resetImage);
    }

    public void testSaveImageAs() {
        System.out.println("saveImageAs test");
        double newBrightness = 1;
        File savePath = new File("test/regression.png");
        FotoFinishModel testModel = new FotoFinishModel();

        testModel.loadGalleryButterflyImage();
        testModel.changeBrightness(newBrightness);
        testModel.saveImageAs(savePath);

        Image savedImage;
        try {
            savedImage = new Image(new FileInputStream(savePath));
        } catch (FileNotFoundException fnfex) {
            System.out.println(false);
            return;
        }
        assertEquals(savedImage.getHeight(), testModel.getImage().getHeight(), 0);
        assertEquals(savedImage.getWidth(), testModel.getImage().getWidth(), 0);
        assertTrue(this.isOneColor(savedImage, Color.WHITE));
    }

    public void testSliderCallsFunction() {
        System.out.println("slider calls function test");
        double radius = 3;
        this.gaussianBlurSlider.setValue(radius);
        assertEquals(model.getGaussianBlurRadius(), radius, 0);
        this.gaussianBlurSlider.setValue(0);
    }
    
    public void testPixelsChanged(){
        System.out.println("number of pixels changed function test");
        
        File fImage = new File("test/sample19x19.png");
        FotoFinishModel testModel = new FotoFinishModel();
        testModel.loadImage(fImage);
        
        assertEquals(testModel.applyGaussianToPixel(0,0,1), 3);    // Testing corner
        assertEquals(testModel.applyGaussianToPixel(9,0,1), 5);    // Testing edge
        assertEquals(testModel.applyGaussianToPixel(9,9,1), 8);    // Testing center
    }

    public void testCorrectPixels() {
        System.out.println("number of pixels visited function test");
        
        File fImage = new File("test/sample19x19.png");
        FotoFinishModel testModel = new FotoFinishModel();
        testModel.loadImage(fImage);

        assertEquals(testModel.applyGaussianBlur(1), 361);    // Testing corner
    }

    public void testBlurOutput() {
        System.out.println("overall blur");
        
        File fImage = new File("test/before4x4.png");
        FotoFinishModel testModel = new FotoFinishModel();
        testModel.loadImage(fImage);
        printImage(SwingFXUtils.fromFXImage(testModel.getImage(), null));
        System.out.println("");
        testModel.applyGaussianBlur(1);

        Image after;
        try {
            after = new Image(new FileInputStream(new File("test/after4x4.png")));
        } catch (FileNotFoundException fnfex) {
            System.out.println(false);
            return;
        }
        printImage(SwingFXUtils.fromFXImage(testModel.getImage(), null));
        System.out.println("");
        printImage(SwingFXUtils.fromFXImage(after, null));
        
        assertTrue(compareImages(SwingFXUtils.fromFXImage(testModel.getImage(), null), SwingFXUtils.fromFXImage(after, null)));
    }

    public static boolean compareImages(BufferedImage imgA, BufferedImage imgB) {
  // The images must be the same size.
  if (imgA.getWidth() == imgB.getWidth() && imgA.getHeight() == imgB.getHeight()) {
    int width = imgA.getWidth();
    int height = imgA.getHeight();

    // Loop over every pixel.
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        // Compare the pixels for equality.
        if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
          return false;
        }
      }
    }
  } else {
    return false;
  }

  return true;
}
    public static void printImage(BufferedImage imgA) {
  // The images must be the same size.
    int width = imgA.getWidth();
    int height = imgA.getHeight();

    // Loop over every pixel.
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        java.awt.Color selectColor = new java.awt.Color(imgA.getRGB(x, y));
        System.out.print(selectColor.getGreen() + " ");
      }
        System.out.println("");
    }
   }
}