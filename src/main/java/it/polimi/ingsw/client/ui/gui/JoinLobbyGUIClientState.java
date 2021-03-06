package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractJoinLobbyClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.guicontrollers.JoinLobbyController;
import it.polimi.ingsw.client.ui.gui.utils.SavedScene;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderBuilder;

/**
 * A GUI-specific JOIN_LOBBY client state.
 */
public class JoinLobbyGUIClientState extends AbstractJoinLobbyClientState implements GUIClientState{
    private SavedScene savedScene;

    /**
     * Instantiates a new JOIN_LOBBY ClientState.
     *
     * @param client the client
     */
    public JoinLobbyGUIClientState(Client client) {
        super(client);
    }

    /**
     * This method is used to send the client to the menu
     */
    public void returnToMenu(){
        client.moveToState(ClientState.WELCOME_SCREEN);
    }

    /**
     * Triggers the operations to perform when exiting the current state
     */
    @Override
    public void tearDown() {
        ((JoinLobbyController)savedScene.controller).stopAnimation();
    }


    @Override
    public void render() {
        SceneLoaderBuilder sceneLoaderBuilder = new SceneLoaderBuilder("/fxml/JoinLobby.fxml", client);
        sceneLoaderBuilder.setState(ClientState.JOIN_LOBBY, this).build().executeSceneChange();
        savedScene = ((GUI)client.getUI()).getCurrentScene();
    }
}
