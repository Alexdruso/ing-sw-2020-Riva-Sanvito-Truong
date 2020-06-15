package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractLoseGameClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoader;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderFactory;

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
        SceneLoader.applyBlurOut(500);
        client.closeConnection();
        client.moveToState(ClientState.CONNECT_TO_SERVER);
    }

    public void returnToMenu(){
        notifyUiInteraction();
        SceneLoader.applyBlurOut(2000);
    }

    public void spectate(){
        SceneLoader.applyBlurOut(2000);
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
            sceneLoaderFactory.setState(ClientState.LOSE_GAME, this)
                    .setFadeIn(false)
                    .setFadeOut(false)
                    .setReplaceOldScene(false)
                    .build()
                    .executeSceneChange();
        }
    }
}
