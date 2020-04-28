package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.JavaFXApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenu {
    Stage primaryStage;

    @FXML
    public void handleCredits(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Credits.fxml"));
        Scene scene = new Scene(root, 1920, 1080);

        scene.getStylesheets().add(getClass().getResource("/css/credits.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/css/common.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }


    @FXML
    public void initialize(){
        primaryStage = JavaFXApp.getPrimaryStage();
    }
}
