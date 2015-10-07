package fotofinish;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
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
    private BrushType brushType;
    private int brushSize;
    private Color brushColor;
    private int brightness;
    private int contrast;
    private final int SLIDER_CHANGE_THRESH = 1;

    //TODO: how can we better synchronize this with FXML?
    public FotoFinishModel() {
        this.brushType = BrushType.CIRCLE;
        this.brushSize = 10;
        this.brightness = 0;
        this.contrast = 0;
    }

    public void loadImage(File file) {
        this.imageFile = file;
        this.reloadImage();
    }

    public void reloadImage() {
        if (this.imageFile != null) {
            try {
                this.image = new Image(new FileInputStream(this.imageFile));
                logger.log(Level.INFO, "loaded file {0} as image", this.imageFile);
            } catch (FileNotFoundException fnfex) {
                logger.log(Level.SEVERE, "error loading file {0} as image{1}", new Object[] {this.imageFile, fnfex});
            }
        }
    }

    public void saveImage() {
        this.saveImageAs(this.imageFile);
    }

    //TODO: save as appropriate file type (not just jpg)
    public void saveImageAs(File file) {
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "jpg", file);
            logger.log(Level.INFO, "saved image as file {0}", file);
        } catch (IOException ioex) {
            logger.log(Level.SEVERE, "error saving image as file {0}", new Object[] {file, ioex});
        }
    }

    public Image getImage() {
        return this.image;
    }

    public void applyGrayscaleFilter() {
        //TODO: code to apply grayscale filter to image
        logger.log(Level.INFO, "applied grayscale filter");
    }

    public void applySepiaFilter() {
        //TODO: code to apply sepia filter to image
        logger.log(Level.INFO, "applied sepia filter");
    }

    public void applyInstantFilter() {
        //TODO: code to apply instant filter to image
        logger.log(Level.INFO, "applied instant filter");
    }

    public void applyCustomFilter() {
        //TODO: code to apply custom filter (INCLUDING ARGS FROM POPUP) to image
        logger.log(Level.INFO, "applied custom filter");
    }

    public boolean changeBrightness(Number newBrightnessNumber) {
        int newBrightness = newBrightnessNumber.intValue();
        if (Math.abs(this.brightness - newBrightness) >= this.SLIDER_CHANGE_THRESH) {
            //TODO: code to adjust brightness
            logger.log(Level.INFO, "brightness changed from {0} to {1}", new Object[] {newBrightness, this.brightness});
            this.brightness = newBrightness;
            return true;
        }
        return false;
    }

    public boolean changeContrast(Number newContrastNumber) {
        int newContrast = newContrastNumber.intValue();
        if (Math.abs(this.contrast - newContrast) >= this.SLIDER_CHANGE_THRESH) {
            //TODO: code to adjust contrast
            logger.log(Level.INFO, "contrast changed from {0} to {1}", new Object[] {newContrast, this.contrast});
            this.contrast = newContrast;
            return true;
        }
        return false;
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
    }

    public void loadGalleryTeddyBearImage() {
        //TODO: load teddy bear image (carefully)
    }

    public void loadGalleryPrincessImage() {
        //TODO: load princess image (carefully)
    }

    public void loadGalleryFirefighterImage() {
        //TODO: load firefighter image (carefully)
    }
}