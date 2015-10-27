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
        
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Foto Finish");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}