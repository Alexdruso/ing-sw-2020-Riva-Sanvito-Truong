package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractWelcomeScreenState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderBuilder;

/**
 * A GUI-specific WELCOME_SCREEN client state.
 */
public class WelcomeScreenGUIClientState extends AbstractWelcomeScreenState implements GUIClientState {
    /**
     * Instantiates a new ClientState.
     *
     * @param client the client
     */
    public WelcomeScreenGUIClientState(Client client) {
        super(client);
    }

    @Override
    public void setup() {
        super.setup();
        client.closeConnection();
    }

    @Override
    public void render() {
        SceneLoaderBuilder sceneLoaderBuilder = new SceneLoaderBuilder("/fxml/MainMenu.fxml", client);
        sceneLoaderBuilder
                .setState(ClientState.WELCOME_SCREEN, this)
                .setFirstFadeOut(false)
                .setFadeInDuration(1000)
                .build()
                .executeSceneChange();
    }
}
