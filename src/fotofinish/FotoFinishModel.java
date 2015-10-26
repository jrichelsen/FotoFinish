package fotofinish;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
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
        /*
         BufferedImage tempBufferedImage = SwingFXUtils.fromFXImage(this.image, null);
         for (int x = 0; x < tempBufferedImage.getWidth(); x++) {
         for (int y = 0; y < tempBufferedImage.getHeight(); y++) {
         java.awt.Color c = new java.awt.Color(tempBufferedImage.getRGB(x, y));
         int r = c.getRed();
         int g = c.getGreen();
         int b = c.getBlue();

         int grayLevel = (r + g + b) / 3;
         int grayRgb = (grayLevel << 16) + (grayLevel << 8) + grayLevel;
         tempBufferedImage.setRGB(x, y, grayRgb);
         }
         }
         this.image = SwingFXUtils.toFXImage(tempBufferedImage, null);
         */
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
        logger.log(Level.INFO, "Saturation");
    }

    public void applyCustomFilter() {
        //TODO: code to apply custom filter (INCLUDING ARGS FROM POPUP) to image
        logger.log(Level.INFO, "TODO: applied custom filter");
    }

    public boolean changeBrightness(double newBrightness) {
        if (Math.abs(this.brightness - newBrightness) >= this.SLIDER_CHANGE_THRESH) {
            /*
             double brilho = (double) newBrightness;
             if (brilho <= 0) {
             brilho = (brilho + 100) / 100; //TODO: what is the point of this?
             } else {
             brilho = 1 + (brilho / 100);
             }
             RescaleOp op = new RescaleOp(brilho, 0, null); //TODO: how does this work?
             BufferedImage tempBufferedImage = SwingFXUtils.fromFXImage(this.image, null);
             op.filter(tempBufferedImage, tempBufferedImage);
             logger.log(Level.INFO, "TODO: brightness changed from {0} to {1}", new Object[]{newBrightness, this.brightness});
             this.brightness = newBrightness;
             */
            this.brightness = newBrightness;
            this.updateBrightnessContrast();
            logger.log(Level.INFO, "TODO: brightness changed to {0}", this.brightness);
            return true;
        }
        return false;
    }

    public boolean changeContrast(double newContrast) {
        if (Math.abs(this.contrast - newContrast) >= this.SLIDER_CHANGE_THRESH) {
            this.contrast = newContrast;
            this.updateBrightnessContrast();
            logger.log(Level.INFO, "TODO: contrast changed to {0}", this.contrast);
            return true;
        }
        return false;
    }

    public boolean changeSaturation(double newSaturation) {
        if (Math.abs(this.contrast - newSaturation) >= this.SLIDER_CHANGE_THRESH) {
            this.saturation = newSaturation;
            this.updateBrightnessContrast();
            logger.log(Level.INFO, "TODO: contrast changed to {0}", this.contrast);
            return true;
        }
        return false;
    }

    private void updateBrightnessContrast() {
        ImageView processor = new ImageView(this.originalImage);
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(this.brightness);
        colorAdjust.setContrast(this.contrast);
        colorAdjust.setSaturation(this.saturation);

        processor.setEffect(colorAdjust);
        this.image = processor.snapshot(null, null);
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
        //TODO: load buttefly image (carefully)
        logger.log(Level.INFO, "TODO: loaded butterfly gallery image");
    }

    public void loadGalleryTeddyBearImage() {
        //TODO: load teddy bear image (carefully)
        logger.log(Level.INFO, "TODO: loaded teddy bear gallery image");
    }

    public void loadGalleryPrincessImage() {
        //TODO: load princess image (carefully)

        logger.log(Level.INFO, "TODO: loaded princess gallery image");
    }

    public void loadGalleryFirefighterImage() {
        //TODO: load firefighter image (carefully)
        logger.log(Level.INFO, "TODO: loaded firefighter gallery image");
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

    public void OpenHelpDialog() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HelpDialog.fxml"));
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
