package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractWinGameClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.guicontrollers.WinGameController;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderBuilder;

/**
 * A GUI-specific WIN_GAME client state.
 */
public class WinGameGUIClientState extends AbstractWinGameClientState implements GUIClientState {
    /**
     * Instantiates a new WIN_GAME ClientState.
     *
     * @param client the client
     */
    public WinGameGUIClientState(Client client) {
        super(client);
    }

    /**
     * This method sends the client to the ConnectToServer state
     */
    public void reconnect(){
        client.moveToState(ClientState.CONNECT_TO_SERVER);
    }

    /**
     * This method sends the client to the menu
     */
    public void returnToMenu(){
        client.moveToState(ClientState.WELCOME_SCREEN);
    }

    @Override
    public void render() {
        SceneLoaderBuilder sceneLoaderBuilder = new SceneLoaderBuilder("/fxml/EndGame.fxml", client);
        sceneLoaderBuilder.setState(ClientState.WIN_GAME, this).build().executeSceneChange();
        WinGameController controller = (WinGameController) ((GUI)client.getUI()).getCurrentScene().controller;
        if (client.isCurrentlyActive()) {
            controller.setWinnerPrompts();
        }
        else {
            controller.setLoserPrompts();
        }
    }
}
