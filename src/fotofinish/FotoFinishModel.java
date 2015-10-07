package fotofinish;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private int currBrightness;
    private int currContrast;
    private final int SLIDER_CHANGE_THRESH = 1;

    //TODO: how can we better synchronize this with FXML?
    public FotoFinishModel() {
        this.brushType = BrushType.CIRCLE;
        this.brushSize = 10;
        currBrightness = 50;
        currContrast = 50;
    }

    public void loadImage(File file) {
        this.imageFile = file;
        this.reloadImage();
    }

    public void reloadImage() {
        if (this.imageFile != null) {
            try {
                this.image = new Image(new FileInputStream(imageFile));
                logger.log(Level.INFO, "loaded file {0} as image", this.imageFile);
            } catch (FileNotFoundException fnfex) {
                logger.log(Level.SEVERE, "error loading file {0} as image{1}", new Object[]{this.imageFile, fnfex});
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
            logger.log(Level.SEVERE, "error saving image as file {0}", new Object[]{file, ioex});
        }
    }

    public Image getImage() {
        return this.image;
    }

    public Image applyGrayscaleFilter(BufferedImage img) {
        for (int x = 0; x < img.getWidth(); ++x) {
            for (int y = 0; y < img.getHeight(); ++y) {
                int rgb = img.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = (rgb & 0xFF);

                int grayLevel = (r + g + b) / 3;
                int gray = (grayLevel << 16) + (grayLevel << 8) + grayLevel;
                img.setRGB(x, y, gray);
            }
        }
        Image image = SwingFXUtils.toFXImage(img, null);
        logger.log(Level.INFO, "Applied grayscale filter");
        return image;
    }

    public ImageView applySepiaFilter(ImageView imagev) {
        imagev.setEffect(new SepiaTone());
        return imagev;
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
        if (Math.abs(this.currBrightness - newBrightness) >= this.SLIDER_CHANGE_THRESH) {
            //TODO: code to adjust currBrightness
            logger.log(Level.INFO, "brightness changed from {0} to {1}", new Object[]{newBrightness, this.currBrightness});
            this.currBrightness = newBrightness;
            return true;
        }
        return false;
    }

    public boolean changeContrast(Number newContrastNumber) {
        int newContrast = newContrastNumber.intValue();
        if (Math.abs(this.currContrast - newContrast) >= this.SLIDER_CHANGE_THRESH) {
            //TODO: code to adjust contrast
            logger.log(Level.INFO, "contrast changed from {0} to {1}", new Object[]{newContrast, this.currContrast});
            this.currContrast = newContrast;
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
