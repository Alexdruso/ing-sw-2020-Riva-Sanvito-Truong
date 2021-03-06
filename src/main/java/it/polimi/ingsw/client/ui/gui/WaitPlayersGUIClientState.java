package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractWaitPlayersClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.guicontrollers.WaitPlayersController;
import it.polimi.ingsw.client.ui.gui.utils.SavedScene;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderBuilder;

/**
 * A GUI-specific WAIT_PLAYERS client state.
 */
public class WaitPlayersGUIClientState extends AbstractWaitPlayersClientState implements GUIClientState {
    private SavedScene savedScene;

    /**
     * Instantiates a new WAIT_PLAYERS ClientState.
     *
     * @param client the client
     */
    public WaitPlayersGUIClientState(Client client) {
        super(client);
    }

    /**
     * This method sends the client to the menu
     */
    public void returnToMenu(){
        client.moveToState(ClientState.WELCOME_SCREEN);
    }

    /**
     * Triggers the operations to perform when exiting the current state
     */
    @Override
    public void tearDown() {
        ((WaitPlayersController)savedScene.controller).stopAnimation();
    }


    @Override
    public void render() {
        SceneLoaderBuilder sceneLoaderBuilder = new SceneLoaderBuilder("/fxml/WaitPlayers.fxml", client);
        sceneLoaderBuilder.setState(ClientState.WAIT_PLAYERS, this).build().executeSceneChange();
        savedScene = ((GUI)client.getUI()).getCurrentScene();
    }
}
