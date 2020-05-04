package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;

public abstract class AbstractClientTurnState {
    protected final Client client;

    public AbstractClientTurnState(Client client) {
        this.client = client;
    }

    public abstract void render();

    public abstract void notifyUiInteraction();

    /**
     * Handles a CLIENT_ERROR StatusMessage sent by the server.
     *
     * @throws UnsupportedOperationException if CLIENT_ERROR is not a valid StatusMessage for the current state
     */
    public void handleClientError() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
