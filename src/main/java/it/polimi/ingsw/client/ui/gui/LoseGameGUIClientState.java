package it.polimi.ingsw.client.ui.gui;

import com.google.gson.internal.bind.util.ISO8601Utils;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractLoseGameClientState;
import it.polimi.ingsw.client.clientstates.AbstractWinGameClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.guicontrollers.WinGameController;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderFactory;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;

import java.util.concurrent.atomic.AtomicBoolean;

public class LoseGameGUIClientState extends AbstractLoseGameClientState implements GUIClientState {
    /**
     * Instantiates a new LOSE_GAME ClientState.
     *
     * @param client the client
     */
    public LoseGameGUIClientState(Client client) {
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

    public void spectate(){
        //This is here to unlock the scene change
        //notifyUiInteraction();
    }

    @Override
    public void render() {
        AtomicBoolean showLoseScreen = new AtomicBoolean(false);
        client.getGame().getPlayer(client.getNickname()).ifPresent(
                player -> {
                    if (!player.isInGame()) {
                        showLoseScreen.set(true);
                    }
                }
        );
        if (showLoseScreen.get()) {
            SceneLoaderFactory sceneLoaderFactory = new SceneLoaderFactory("/fxml/LoseGame.fxml", client);
            sceneLoaderFactory.setState(ClientState.LOSE_GAME, this).build().executeSceneChange();
        }
    }
}
