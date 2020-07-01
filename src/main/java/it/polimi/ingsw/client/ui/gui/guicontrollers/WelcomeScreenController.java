package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.JavaFXGUI;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderBuilder;
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

    /**
     * This method sends the client to the CONNECT_TO_SERVER state
     * @param event the mouse click event
     */
    @FXML
    void handleJoinLobby(ActionEvent event) {
        client.moveToState(ClientState.CONNECT_TO_SERVER);
    }

    /**
     * This method sends to the client to the Credits screen
     * @param event the mouse click event
     */
    @FXML
    void handleCredits(ActionEvent event) {
        SceneLoaderBuilder sceneLoaderBuilder = new SceneLoaderBuilder("/fxml/Credits.fxml", client);
        sceneLoaderBuilder.build().executeSceneChange();
    }

    /**
     * This method sends the client to the Settings screen
     * @param event the mouse click event
     */
    @FXML
    void handleSettings(ActionEvent event) {
        SceneLoaderBuilder sceneLoaderBuilder = new SceneLoaderBuilder("/fxml/Settings.fxml", client);
        sceneLoaderBuilder.build().executeSceneChange();
    }

    /**
     * JavaFX initialization method
     */
    @FXML
    void initialize(){
        primaryStage = JavaFXGUI.getPrimaryStage();
    }

    @Override
    public void handleError(String message) {
        LOGGER.log(Level.SEVERE, message);
    }
}
