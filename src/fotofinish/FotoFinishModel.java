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
import javafx.scene.effect.Glow;
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
    private double redOffset;
    private double greenOffset;
    private double blueOffset;
    private double brightness;
    private double contrast;
    private double saturation;
    File galleryButterflyFile = new File("src/img/galleryButterfly.jpg");
    File galleryTeddyBearFile = new File("src/img/galleryTeddyBear.jpg");
    File galleryPrincessFile = new File("src/img/galleryPrincess.jpg");
    File galleryFirefighterFile = new File("src/img/galleryFirefighter.jpg");
    private Color brushColor;
    private BrushType brushType;
    private int brushSize;
    private final double SLIDER_CHANGE_THRESH = 0.01;
    private final double RGB_SLIDER_CHANGE_THRESH = 5;

    //TODO: how can we better synchronize this with FXML?
    public FotoFinishModel() {
        this.redOffset = 0;
        this.greenOffset = 0;
        this.blueOffset = 0;
        this.brightness = 0;
        this.contrast = 0;
        this.saturation = 0;
        this.brushType = BrushType.CIRCLE;
        this.brushSize = 10;
        
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

    public void openHelpDocument() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HelpDocument.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Help");
            stage.setScene(new Scene(root));
            stage.show();
            logger.log(Level.INFO, "help document launched");
        } catch (IOException ioex) {
            logger.log(Level.SEVERE, "error opening help document", ioex);
        }
    }

    public void openAboutDialog() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AboutDialog.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("About");
            stage.setScene(new Scene(root));
            stage.show();
            logger.log(Level.INFO, "about dialog created");
        } catch (IOException ioex) {
            logger.log(Level.SEVERE, "error opening about dialog", ioex);
        }
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
        ImageView processor = new ImageView(this.originalImage);

        ColorAdjust lowSaturation = new ColorAdjust();
        lowSaturation.setSaturation(-0.3);
        processor.setEffect(lowSaturation);
        processor.setEffect(new Glow(0.3));

        this.image = processor.snapshot(null, null); //TODO: what is first parameter?

        this.changeRed(-30);
        this.changeBlue(30);
        logger.log(Level.INFO, "applied instant filter");
    }

    public void launchCustomFilterPopup(FotoFinishMainController mainController, FotoFinishModel model) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CustomFilterPopup.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            CustomFilterPopupController controller = fxmlLoader.getController();
            controller.setMainController(mainController);
            controller.setModel(model);
            Stage stage = new Stage();
            stage.setTitle("Custom Filter");
            stage.setScene(new Scene(root));
            stage.show();
            logger.log(Level.INFO, "custom filter popup launched");
        } catch (IOException ioex) {
            logger.log(Level.SEVERE, "error launching custom filter popup", ioex);
        }
    }

    public boolean changeRed(double newRed) {
        if (Math.abs(this.redOffset - newRed) >= this.RGB_SLIDER_CHANGE_THRESH) {
            this.redOffset = newRed;
            this.applyCustomFilter();
            logger.log(Level.INFO, "red level changed to {0}", this.redOffset);
            return true;
        }
        return false;
    }

    public boolean changeGreen(double newGreen) {
        if (Math.abs(this.greenOffset - newGreen) >= this.RGB_SLIDER_CHANGE_THRESH) {
            this.greenOffset = newGreen;
            this.applyCustomFilter();
            logger.log(Level.INFO, "green level changed to {0}", this.greenOffset);
            return true;
        }
        return false;
    }

    public boolean changeBlue(double newBlue) {
        if (Math.abs(this.blueOffset - newBlue) >= this.RGB_SLIDER_CHANGE_THRESH) {
            this.blueOffset = newBlue;
            this.applyCustomFilter();
            logger.log(Level.INFO, "blue level changed to {0}", this.blueOffset);
            return true;
        }
        return false;
    }

    public void applyCustomFilter() {
        BufferedImage RGBBufferedImage = SwingFXUtils.fromFXImage(this.originalImage, null);
        for (int y = 0; y < RGBBufferedImage.getHeight(); y++) {
            for (int x = 0; x < RGBBufferedImage.getWidth(); x++) {
                int RGB = RGBBufferedImage.getRGB(x, y);
                java.awt.Color oldColor = new java.awt.Color(RGB);
                int newRed = oldColor.getRed() + (int)Math.round(this.redOffset);
                int newGreen = oldColor.getGreen() + (int)Math.round(this.greenOffset);
                int newBlue = oldColor.getBlue() + (int)Math.round(this.blueOffset);
                java.awt.Color newColor = new java.awt.Color(
                    newRed > 255 ? 255 : (newRed < 0 ? 0 : newRed),
                    newGreen > 255 ? 255 : (newGreen < 0 ? 0 : newGreen),
                    newBlue > 255 ? 255 : (newBlue < 0 ? 0 : newBlue)
                );
                RGBBufferedImage.setRGB(x, y, newColor.getRGB());
            }
        }
        this.image = SwingFXUtils.toFXImage(RGBBufferedImage, null);
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

    public void changeBrushColor(Color newBrushColor) {
        this.brushColor = newBrushColor;
        logger.log(Level.INFO, "brush color set to {0}", this.brushColor);
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

    public void changeBrushSize(int newBrushSize) {
        this.brushSize = newBrushSize;
        logger.log(Level.INFO, "brush size set to {0}", this.brushSize);
    }

    public Image getImage() {
        return this.image;
    } 

    public void resetImageToOriginal() {
        this.image = this.originalImage;
    }
}