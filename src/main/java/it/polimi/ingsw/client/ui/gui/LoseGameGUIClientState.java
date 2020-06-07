package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractLoseGameClientState;
import it.polimi.ingsw.client.clientstates.AbstractWinGameClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.guicontrollers.WinGameController;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderFactory;

public class LoseGameGUIClientState extends AbstractLoseGameClientState implements GUIClientState {
    /**
     * Instantiates a new LOSE_GAME ClientState.
     *
     * @param client the client
     */
    public LoseGameGUIClientState(Client client) {
        super(client);
    }

    public void joinLobby(){
        // TODO
    }

    public void returnToMenu(){
        // TODO
    }

    @Override
    public void render() {
        // TODO (see CLI for example)
    }
}
