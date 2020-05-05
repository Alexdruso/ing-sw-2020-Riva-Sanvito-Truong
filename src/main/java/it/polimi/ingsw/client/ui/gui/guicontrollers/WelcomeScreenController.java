package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.JavaFXApp;
import it.polimi.ingsw.client.clientstates.ClientState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WelcomeScreenController extends AbstractController {
    Stage primaryStage;

    @FXML
    public void handleJoinLobby(ActionEvent event) {
        client.moveToState(ClientState.CONNECT_TO_SERVER);
    }

    @FXML
    public void handleCredits(ActionEvent event) throws Exception{
        //TODO: fix this
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Credits.fxml"));
        Scene scene = new Scene(root, 1280, 720);

        scene.getStylesheets().add(getClass().getResource("/css/credits.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/css/common.css").toExternalForm());

        primaryStage.setFullScreen(false);
        primaryStage.show();
    }

    @FXML
    public void initialize(){
        primaryStage = JavaFXApp.getPrimaryStage();
    }

    @Override
    public void handleError(String message) {
        //No error to handle
    }
}
