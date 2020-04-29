package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.UI;

public class GUI extends UI {
    @Override
    public void init() {

    }

    @Override
    public AbstractClientState getClientState(ClientState clientState, Client client) {
        return null;
    }

    @Override
    public void notifyError(String message) {

    }
}
