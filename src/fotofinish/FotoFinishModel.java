package fotofinish;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

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

    //TODO: how can we better synchronize this with FXML?
    public FotoFinishModel() {
        this.brushType = BrushType.CIRCLE;
        this.brushSize = 10;
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

    public Image getImage() {
        return this.image;
    }
}