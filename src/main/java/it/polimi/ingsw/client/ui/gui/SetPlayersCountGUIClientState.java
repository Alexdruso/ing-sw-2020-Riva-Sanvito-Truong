package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractSetPlayersCountClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderFactory;

public class SetPlayersCountGUIClientState extends AbstractSetPlayersCountClientState implements GUIClientState {
    /**
     * Instantiates a new SET_PLAYERS_COUNT ClientState.
     *
     * @param client the client
     */
    public SetPlayersCountGUIClientState(Client client) {
        super(client);
    }

    public void returnToMenu(){
        client.moveToState(ClientState.WELCOME_SCREEN);
    }

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
