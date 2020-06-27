package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.JavaFXGUI;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the WelcomeScreen state
 */
public class WelcomeScreenController extends AbstractController {
    Stage primaryStage;
    private static final Logger LOGGER = Logger.getLogger(WelcomeScreenController.class.getName());

    @FXML
    void handleJoinLobby(ActionEvent event) {
        client.moveToState(ClientState.CONNECT_TO_SERVER);
    }

    @FXML
    void handleCredits(ActionEvent event) {
        SceneLoaderFactory sceneLoaderFactory = new SceneLoaderFactory("/fxml/Credits.fxml", client);
        sceneLoaderFactory.build().executeSceneChange();
    }

    @FXML
    void handleSettings(ActionEvent event) {
        SceneLoaderFactory sceneLoaderFactory = new SceneLoaderFactory("/fxml/Settings.fxml", client);
        sceneLoaderFactory.build().executeSceneChange();
    }

    @FXML
    void initialize(){
        primaryStage = JavaFXGUI.getPrimaryStage();
    }

    @Override
    public void handleError(String message) {
        LOGGER.log(Level.SEVERE, message);
    }
}
