package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractLoseGameClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoader;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderBuilder;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A GUI-specific LOSE_GAME client state.
 */
public class LoseGameGUIClientState extends AbstractLoseGameClientState implements GUIClientState {
    /**
     * Instantiates a new LOSE_GAME ClientState.
     *
     * @param client the client
     */
    public LoseGameGUIClientState(Client client) {
        super(client);
    }

    /**
     * This method sends the client to the ConnectToServer state and removes the overlay
     */
    public void reconnect(){
        SceneLoader.applyBlurOut(500);
        client.moveToState(ClientState.CONNECT_TO_SERVER);
    }

    /**
     * This method sends the client to the menu and removes the overlay
     */
    public void returnToMenu(){
        notifyUiInteraction();
        SceneLoader.applyBlurOut(2000);
    }

    /**
     * This method removes the overlay, leaving the player to spectate the game
     */
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
            SceneLoaderBuilder sceneLoaderBuilder = new SceneLoaderBuilder("/fxml/LoseGame.fxml", client);
            sceneLoaderBuilder.setState(ClientState.LOSE_GAME, this)
                    .setFadeIn(false)
                    .setFadeOut(false)
                    .setReplaceOldScene(false)
                    .build()
                    .executeSceneChange();
        }
    }
}
