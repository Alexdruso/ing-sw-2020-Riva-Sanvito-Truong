package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractWinGameClientState;

public class WinGameGUIClientState extends AbstractWinGameClientState implements GUIClientState{
    public WinGameGUIClientState(Client client) {
        super(client);
    }

    /**
     * Function called by the main thread that renders the current state to the UI.
     * This function is the only one of this class allowed to be synchronous with the user input.
     */
    @Override
    public void render() {

    }
}
