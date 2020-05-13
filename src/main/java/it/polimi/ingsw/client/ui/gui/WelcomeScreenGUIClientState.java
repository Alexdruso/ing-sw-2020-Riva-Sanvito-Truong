package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.JavaFXApp;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractWelcomeScreenState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class WelcomeScreenGUIClientState extends AbstractWelcomeScreenState implements GUIClientState {
    private final GUI gui;
    private final Scene mainScene;
    private final Stage primaryStage;
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());

    /**
     * Instantiates a new ClientState.
     *
     * @param client the client
     */
    public WelcomeScreenGUIClientState(Client client) {
        super(client);
        gui = (GUI)client.getUI();
        primaryStage = JavaFXApp.getPrimaryStage();
        mainScene = JavaFXApp.getPrimaryScene();
    }

    /**
     * Function called by the main thread that renders the current state to the UI.
     * This function is the only one of this class allowed to be synchronous with the user input.
     * Please, be aware that calls to this function must be either:
     * - guaranteed not to happen concurrently with a Client state change
     * - or the implementation of this function must be self-sufficient (i.e., it does not depend on calls of render of previous states)
     */
    @Override
    public void render() {
        mainScene.getStylesheets().add(getClass().getResource("/css/main-menu.css").toExternalForm());
        SceneLoader.loadFromFXML("/fxml/MainMenu.fxml", mainScene, client, this, ClientState.WELCOME_SCREEN, false, false);
    }
}
