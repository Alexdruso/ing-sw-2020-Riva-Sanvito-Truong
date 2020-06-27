package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractInGameClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoader;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderFactory;

/**
 * A GUI-specific IN_GAME client state.
 */
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

    /**
     * This method sends the client to the menu
     */
    public void returnToMenu(){
        client.moveToState(ClientState.WELCOME_SCREEN);
    }


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
