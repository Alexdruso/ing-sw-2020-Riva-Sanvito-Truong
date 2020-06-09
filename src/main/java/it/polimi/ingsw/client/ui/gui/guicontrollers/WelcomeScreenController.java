package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.JavaFXApp;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class WelcomeScreenController extends AbstractController {
    Stage primaryStage;

    @FXML
    public void handleJoinLobby(ActionEvent event) {
        client.moveToState(ClientState.CONNECT_TO_SERVER);
    }

    @FXML
    public void handleCredits(ActionEvent event) {
        SceneLoaderFactory sceneLoaderFactory = new SceneLoaderFactory("/fxml/Credits.fxml", client);
        sceneLoaderFactory.addCSSFile("/css/credits.css").build().executeSceneChange();
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
