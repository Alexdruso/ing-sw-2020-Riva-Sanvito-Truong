package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractDisconnectClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoader;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderFactory;

/**
 * A GUI-specific DISCONNECT client state.
 */
public class DisconnectGUIClientState extends AbstractDisconnectClientState implements GUIClientState{
    /**
     * Instantiates a new DISCONNECT ClientState.
     *
     * @param client the client
     */
    public DisconnectGUIClientState(Client client) {
        super(client);
    }

    /**
     * This method is used to send the client to the ConnectToServer state and to remove the overlay
     */
    public void reconnect(){
        SceneLoader.applyBlurOut(500);
        notifyUiInteraction();
    }

    /**
     * This method is used to send the client to the menu and to remove the overlay
     */
    public void returnToMenu(){
        SceneLoader.applyBlurOut(2000);
        client.moveToState(ClientState.WELCOME_SCREEN);
    }


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
