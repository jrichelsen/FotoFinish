package fotofinish;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class FotoFinishModel {

    private enum BrushType {
        CIRCLE,
        SQUARE,
        SPRAYPAINT
    }

    private static final Logger logger = Logger.getLogger(fotofinish.FotoFinishModel.class.getName());

    private File imageFile;
    private Image image;
    private Image originalImage;
    File galleryButterflyFile = new File("src/img/galleryButterfly.jpg");
    File galleryTeddyBearFile = new File("src/img/galleryTeddyBear.jpg");
    File galleryPrincessFile = new File("src/img/galleryPrincess.jpg");
    File galleryFirefighterFile = new File("src/img/galleryFirefighter.jpg");
    private BrushType brushType;
    private int brushSize;
    private Color brushColor;
    private double brightness;
    private double contrast;
    private double saturation;
    private final double SLIDER_CHANGE_THRESH = 0.01;

    //TODO: how can we better synchronize this with FXML?
    public FotoFinishModel() {
        this.brushType = BrushType.CIRCLE;
        this.brushSize = 10;
        this.brightness = 0;
        this.contrast = 0;
        this.saturation = 0;
    }

    public void loadImage(File file) {
        this.imageFile = file;
        try {
            this.originalImage = new Image(new FileInputStream(this.imageFile));
            this.image = this.originalImage;
            logger.log(Level.INFO, "loaded file {0} as image", this.imageFile);
        } catch (FileNotFoundException fnfex) {
            logger.log(Level.SEVERE, "error loading file {0} as image{1}", new Object[]{this.imageFile, fnfex});
        }
    }

    public void resetImageToOriginal() {
        this.image = this.originalImage;
    }

    public void saveImage() {
        this.saveImageAs(this.imageFile);
    }

    //TODO: save as appropriate file type (not just png)
    public void saveImageAs(File file) {
        try {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(this.image, null);
            ImageIO.write(bufferedImage, "png", file);
            logger.log(Level.INFO, "saved image as file {0}", file);
        } catch (IOException ioex) {
            logger.log(Level.SEVERE, "error saving image as file {0}", new Object[]{file, ioex});
        }
    }

    public Image getImage() {
        return this.image;
    }

    public void applyGrayscaleFilter() {
        ImageView processor = new ImageView(this.originalImage);

        ColorAdjust grayscaleTone = new ColorAdjust();
        grayscaleTone.setSaturation(-1);

        processor.setEffect(grayscaleTone);
        this.image = processor.snapshot(null, null); //TODO: what is first parameter?
        logger.log(Level.INFO, "applied grayscale filter");
    }

    public void applySepiaFilter() {
        ImageView processor = new ImageView(this.originalImage);
        processor.setEffect(new SepiaTone());
        this.image = processor.snapshot(null, null); //TODO: what is first parameter?
        logger.log(Level.INFO, "applied sepia filter");
    }

    public void applyInstantFilter() {
        logger.log(Level.INFO, "applied instant filter");
    }

    public void applyCustomFilter() {
        //TODO: code to apply custom filter (INCLUDING ARGS FROM POPUP) to image
        logger.log(Level.INFO, "applied custom filter");
    }

    public boolean changeBrightness(double newBrightness) {
        if (Math.abs(this.brightness - newBrightness) >= this.SLIDER_CHANGE_THRESH) {
            this.brightness = newBrightness;
            this.updateBrightnessContrastSaturation();
            logger.log(Level.INFO, "brightness changed to {0}", this.brightness);
            return true;
        }
        return false;
    }

    public boolean changeContrast(double newContrast) {
        if (Math.abs(this.contrast - newContrast) >= this.SLIDER_CHANGE_THRESH) {
            this.contrast = newContrast;
            this.updateBrightnessContrastSaturation();
            logger.log(Level.INFO, "contrast changed to {0}", this.contrast);
            return true;
        }
        return false;
    }

    public boolean changeSaturation(double newSaturation) {
        if (Math.abs(this.contrast - newSaturation) >= this.SLIDER_CHANGE_THRESH) {
            this.saturation = newSaturation;
            this.updateBrightnessContrastSaturation();
            logger.log(Level.INFO, "saturation changed to {0}", this.saturation);
            return true;
        }
        return false;
    }

    private void updateBrightnessContrastSaturation() {
        ImageView processor = new ImageView(this.originalImage);
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(this.brightness);
        colorAdjust.setContrast(this.contrast);
        colorAdjust.setSaturation(this.saturation);

        processor.setEffect(colorAdjust);
        this.image = processor.snapshot(null, null);
        logger.log(Level.INFO, "brighness, contast, and saturation levels updated image");
    }

    public void setBrushTypeCircle() {
        this.brushType = BrushType.CIRCLE;
        logger.log(Level.INFO, "brush type set to circle");
    }

    public void setBrushTypeSquare() {
        this.brushType = BrushType.SQUARE;
        logger.log(Level.INFO, "brush type set to square");
    }

    public void setBrushTypeSpraypaint() {
        this.brushType = BrushType.SPRAYPAINT;
        logger.log(Level.INFO, "brush type set to spraypaint");
    }

    public void changeBrushColor(Color newBrushColor) {
        this.brushColor = newBrushColor;
        logger.log(Level.INFO, "brush color set to {0}", this.brushColor);
    }

    public void changeBrushSize(int newBrushSize) {
        this.brushSize = newBrushSize;
        logger.log(Level.INFO, "brush size set to {0}", this.brushSize);
    }

    public void loadGalleryButterflyImage() {
        this.loadImage(this.galleryButterflyFile);
    }

    public void loadGalleryTeddyBearImage() {
        this.loadImage(this.galleryTeddyBearFile);
    }

    public void loadGalleryPrincessImage() {
        this.loadImage(this.galleryPrincessFile);
    }

    public void loadGalleryFirefighterImage() {
        this.loadImage(this.galleryFirefighterFile);
    }

    public void OpenAboutDialog() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AboutDialog.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("About");
            stage.setWidth(650);
            stage.setHeight(450);
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void OpenHelpDocument() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HelpDocument.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("About");
            stage.setWidth(650);
            stage.setHeight(450);
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
