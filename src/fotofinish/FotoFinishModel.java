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
        return;
    }

    public void reloadImage() {
        try {
            this.image = new Image(new FileInputStream(imageFile));
        } catch (FileNotFoundException fnfex) {
            Logger.getLogger(FotoFinishModel.class.getName()).log(Level.SEVERE, null, fnfex);
        }
        return;
    }

    public void saveImage() {
        this.saveImageAs(this.imageFile);
    }

    //TODO: save as appropriate file type (not just jpg)
    public void saveImageAs(File file) {
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "jpg", file);
        } catch (IOException ioex) {
            Logger.getLogger(FotoFinishModel.class.getName()).log(Level.SEVERE, null, ioex);
        }
    }

    public Image getImage() {
        return this.image;
    }

    public void applyGrayscaleFilter() {
        //TODO: code to apply grayscale filter to image
        return;
    }

    public void applySepiaFilter() {
        //TODO: code to apply sepia filter to image
        return;
    }

    public void applyInstantFilter() {
        //TODO: code to apply instant filter to image
        return;
    }

    public void applyCustomFilter() {
        //TODO: code to apply custom filter (INCLUDING ARGS FROM POPUP) to image
        return;
    }

    public boolean changeBrightness(int brightness) {
        if (Math.abs(this.currBrightness - brightness) > this.SLIDER_CHANGE_THRESH) {
           //TODO: code to adjust brightness 
        }
        return false;
    }

    public boolean changeContrast(int contrast) {
        if (Math.abs(this.currContrast - contrast) > this.SLIDER_CHANGE_THRESH) {
            //TODO: code to adjust contrast
        }
        return false;
    }
}