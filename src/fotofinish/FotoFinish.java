package fotofinish;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FotoFinish extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FotoFinishMain.fxml"));
        Parent root = loader.load();
        FotoFinishMainController controller = loader.getController();
        controller.setStage(stage);
        stage.setMinWidth(600);
        stage.setMinHeight(800);
        stage.setWidth(1000);
        stage.setHeight(600);
        stage.setX(0);
        stage.setY(0);
        
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Foto Finish");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}