package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractDisconnectClientState;

public class DisconnectGUIClientState extends AbstractDisconnectClientState implements GUIClientState{
    /**
     * Instantiates a new DISCONNECT ClientState.
     *
     * @param client the client
     */
    public DisconnectGUIClientState(Client client) {
        super(client);
    }

    /**
     * Function called by the main thread that renders the current state to the UI.
     * This function is the only one of this class allowed to be synchronous with the user input.
     * Please, be aware that calls to this function must be either:
     * - guaranteed not to happen concurrently with a Client state change
     * - or the implementation of this function must be self-sufficient (i.e., it does not depend on calls of render of previous states)
     */
    @Override
    public void render() {

    }

    /**
     * Function called by the UI after the user has provided his/her inputs.
     */
    @Override
    public void notifyUiInteraction() {

    }
}
