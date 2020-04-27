package it.polimi.ingsw;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class JavaFXApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Santorini - GC02");
        Label label = new Label("testlabel");
        Scene scene = new Scene(label, 1920, 1080);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
