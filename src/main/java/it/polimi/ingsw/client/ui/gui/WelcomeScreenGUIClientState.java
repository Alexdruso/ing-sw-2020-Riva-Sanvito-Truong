package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractWelcomeScreenState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.utils.CSSFile;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderFactory;

public class WelcomeScreenGUIClientState extends AbstractWelcomeScreenState implements GUIClientState {
    /**
     * Instantiates a new ClientState.
     *
     * @param client the client
     */
    public WelcomeScreenGUIClientState(Client client) {
        super(client);
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
        SceneLoaderFactory sceneLoaderFactory = new SceneLoaderFactory("/fxml/MainMenu.fxml", client);
        sceneLoaderFactory
                .setState(ClientState.WELCOME_SCREEN, this)
                .setFirstFadeOut(false)
                .setFadeInDuration(2000)
                .build()
                .executeSceneChange();
    }
}
