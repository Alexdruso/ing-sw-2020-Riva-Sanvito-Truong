package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractDisconnectClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoader;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderFactory;

public class DisconnectGUIClientState extends AbstractDisconnectClientState implements GUIClientState{
    /**
     * Instantiates a new DISCONNECT ClientState.
     *
     * @param client the client
     */
    public DisconnectGUIClientState(Client client) {
        super(client);
    }

    public void reconnect(){
        SceneLoader.applyBlurOut(500);
        notifyUiInteraction();
    }

    public void returnToMenu(){
        SceneLoader.applyBlurOut(2000);
        client.closeConnection();
        client.moveToState(ClientState.WELCOME_SCREEN);
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
        SceneLoaderFactory sceneLoaderFactory = new SceneLoaderFactory("/fxml/Disconnect.fxml", client);
        sceneLoaderFactory.setState(ClientState.DISCONNECT, this)
                .setFadeIn(false)
                .setFadeOut(false)
                .setReplaceOldScene(false)
                .build()
                .executeSceneChange();
    }
}
