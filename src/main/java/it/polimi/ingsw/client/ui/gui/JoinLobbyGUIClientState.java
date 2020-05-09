package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.JavaFXApp;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractJoinLobbyClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.guicontrollers.JoinLobbyController;
import it.polimi.ingsw.client.ui.gui.utils.SavedScene;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JoinLobbyGUIClientState extends AbstractJoinLobbyClientState implements GUIClientState{
    private final GUI gui;
    private final Stage primaryStage;
    private final Scene mainScene;
    private SavedScene savedScene;
    /**
     * Instantiates a new JOIN_LOBBY ClientState.
     *
     * @param client the client
     */
    public JoinLobbyGUIClientState(Client client) {
        super(client);
        gui = (GUI)client.getUI();
        primaryStage = JavaFXApp.getPrimaryStage();
        mainScene = primaryStage.getScene();
    }

    public void returnToMenu(){
        client.moveToState(ClientState.WELCOME_SCREEN);
        client.closeConnection();
    }

    /**
     * Triggers the operations to perform when exiting the current state
     */
    @Override
    public void tearDown() {
        ((JoinLobbyController)savedScene.controller).stopAnimation();
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
        savedScene = SceneLoader.loadFromFXML("/fxml/JoinLobby.fxml", mainScene, client, this, ClientState.JOIN_LOBBY, true);
    }
}
