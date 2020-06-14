package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractWaitPlayersClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.guicontrollers.WaitPlayersController;
import it.polimi.ingsw.client.ui.gui.utils.SavedScene;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderFactory;

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

    public void returnToMenu(){
        client.moveToState(ClientState.WELCOME_SCREEN);
        client.closeConnection();
    }

    /**
     * Triggers the operations to perform when exiting the current state
     */
    @Override
    public void tearDown() {
        ((WaitPlayersController)savedScene.controller).stopAnimation();
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
        //FIXME: this synchronization will be replaced with a render queue in the Client
        SceneLoaderFactory sceneLoaderFactory = new SceneLoaderFactory("/fxml/WaitPlayers.fxml", client);
        sceneLoaderFactory.setState(ClientState.WAIT_PLAYERS, this).build().executeSceneChange();
        savedScene = ((GUI)client.getUI()).getCurrentScene();
    }
}
