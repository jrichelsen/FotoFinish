package fotofinish;

import java.io.File;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class FotoFinishModelTest {
    
    public FotoFinishModelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        //new JFXPanel();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    private boolean isOneColor(Image img, Color desiredColor) {
        PixelReader pixRead = img.getPixelReader();
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color pixelColor = pixRead.getColor(x, y);
                if (pixelColor != desiredColor) {
                    return false;
                }
            }
        }
        return true;
    }

    private void describeImage(Image img) {
        PixelReader pixRead = img.getPixelReader();
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                System.out.println(pixRead.getColor(x, y));
            }
        }
    }

    @Test
    public void testChangeBrightnessMin() {
        System.out.println("changeBrightness to min");
        double newBrightness = -1;
        FotoFinishModel instance = new FotoFinishModel();
        instance.loadGalleryButterflyImage();
        instance.changeBrightness(newBrightness);
        assertTrue(this.isOneColor(instance.getImage(), Color.BLACK));
    }

    @Test
    public void testChangeBrightnessMax() {
        System.out.println("changeBrightness to max");
        double newBrightness = 1;
        FotoFinishModel instance = new FotoFinishModel();
        instance.loadGalleryButterflyImage();
        instance.changeBrightness(newBrightness);
        assertTrue(this.isOneColor(instance.getImage(), Color.WHITE));
    }

    @Test
    public void testGetImage() {
        System.out.println("getImage");
        FotoFinishModel instance = new FotoFinishModel();
        Image expResult = null;
        Image result = instance.getImage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testResetImageToOriginal() {
        System.out.println("resetImageToOriginal");
        FotoFinishModel instance = new FotoFinishModel();
        instance.resetImageToOriginal();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testSaveImageAs() {
        System.out.println("saveImageAs");
        File file = null;
        FotoFinishModel instance = new FotoFinishModel();
        instance.saveImageAs(file);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}