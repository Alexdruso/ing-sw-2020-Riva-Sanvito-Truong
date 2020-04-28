package it.polimi.ingsw;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class JavaFXApp extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        stage.setTitle("Santorini - GC02");

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        Scene scene = new Scene(root, 1920, 1080);

        scene.getStylesheets().add(getClass().getResource("/css/MainMenu.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/css/common.css").toExternalForm());

        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public static Stage getPrimaryStage(){
        return primaryStage;
    }

    public static void setPrimaryStage(Stage stage){
        primaryStage = stage;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
