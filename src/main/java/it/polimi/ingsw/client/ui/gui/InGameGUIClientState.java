package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractInGameClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoader;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderFactory;

public class InGameGUIClientState extends AbstractInGameClientState implements GUIClientState{
    private SceneLoader sceneLoader;
    /**
     * Instantiates a new IN_GAME ClientState.
     *
     * @param client the client
     */
    public InGameGUIClientState(Client client) {
        super(client);
    }

    public void returnToMenu(){
        client.moveToState(ClientState.WELCOME_SCREEN);
        client.closeConnection();
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
        if(sceneLoader == null){
            //Initial render, specific renderings are handled by ClientTurnStates
            SceneLoaderFactory sceneLoaderFactory = new SceneLoaderFactory("/fxml/InGame.fxml", client);
            sceneLoader = sceneLoaderFactory.setState(ClientState.IN_GAME, this).build();
        }
        sceneLoader.executeSceneChange();
        client.getGame().getTurn().getTurnState().render();
    }
}
