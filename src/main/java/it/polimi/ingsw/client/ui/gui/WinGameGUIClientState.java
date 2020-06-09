package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractWinGameClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.guicontrollers.InGameController;
import it.polimi.ingsw.client.ui.gui.guicontrollers.WinGameController;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderFactory;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import javafx.scene.Scene;

public class WinGameGUIClientState extends AbstractWinGameClientState implements GUIClientState {
    /**
     * Instantiates a new WIN_GAME ClientState.
     *
     * @param client the client
     */
    public WinGameGUIClientState(Client client) {
        super(client);
    }

    public void reconnect(){
        client.closeConnection();
        client.moveToState(ClientState.CONNECT_TO_SERVER);
    }

    public void returnToMenu(){
        client.closeConnection();
        client.moveToState(ClientState.WELCOME_SCREEN);
    }

    @Override
    public void render() {
        SceneLoaderFactory sceneLoaderFactory = new SceneLoaderFactory("/fxml/EndGame.fxml", client);
        sceneLoaderFactory.setState(ClientState.WIN_GAME, this).build().executeSceneChange();
        WinGameController controller = (WinGameController) ((GUI)client.getUI()).getCurrentScene().controller;
        if (client.isCurrentlyActive()) {
            controller.setWinnerPrompts();
        }
        else {
            controller.setLoserPrompts();
        }
    }
}
