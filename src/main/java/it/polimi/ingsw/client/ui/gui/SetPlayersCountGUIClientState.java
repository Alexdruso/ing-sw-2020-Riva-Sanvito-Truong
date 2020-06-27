package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractSetPlayersCountClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderFactory;

/**
 * A GUI-specific SET_PLAYERS_COUNT client state.
 */
public class SetPlayersCountGUIClientState extends AbstractSetPlayersCountClientState implements GUIClientState {
    /**
     * Instantiates a new SET_PLAYERS_COUNT ClientState.
     *
     * @param client the client
     */
    public SetPlayersCountGUIClientState(Client client) {
        super(client);
    }

    /**
     * This method sends the client to the menu
     */
    public void returnToMenu(){
        client.moveToState(ClientState.WELCOME_SCREEN);
    }

    /**
     * This method notifies the client that the player count has been chosen
     * @param playersCount the player count
     */
    public void setPlayersCount(int playersCount){
        this.playersCount = playersCount;
        notifyUiInteraction();
    }


    @Override
    public void render() {
        SceneLoaderFactory sceneLoaderFactory = new SceneLoaderFactory("/fxml/SetPlayersCount.fxml", client);
        sceneLoaderFactory.setState(ClientState.SET_PLAYERS_COUNT, this).build().executeSceneChange();
    }
}
