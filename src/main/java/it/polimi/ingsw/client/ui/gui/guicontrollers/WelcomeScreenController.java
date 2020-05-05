package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.JavaFXApp;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoader;
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
    public void handleCredits(ActionEvent event) {
        primaryStage.getScene().getStylesheets().add(getClass().getResource("/css/credits.css").toExternalForm());
        SceneLoader.loadNoCacheFromFXML("/fxml/Credits.fxml", client, primaryStage.getScene(), true);
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
