package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;

/**
 * A generic client turn state, to be extended by a client turn state.
 * Each client turn state represents a different phase of a turn of the server (build, move, ...)
 *
 * @see ClientTurnState
 */
public abstract class AbstractClientTurnState {
    protected final Client client;

    /**
     * Initializes the turn state.
     *
     * @param client the reference to the Client
     */
    public AbstractClientTurnState(Client client) {
        this.client = client;
    }

    /**
     * Function called by the main thread that renders the current state to the UI.
     * This function is the only one of this class allowed to be synchronous with the user input.
     */
    public abstract void render();

    /**
     * Function called by the UI after the user has provided his/her inputs.
     */
    public abstract void notifyUiInteraction();

    /**
     * Handles a CLIENT_ERROR StatusMessage sent by the server.
     *
     * @throws UnsupportedOperationException if CLIENT_ERROR is not a valid StatusMessage for the current state
     */
    public void handleClientError() {
        throw new UnsupportedOperationException();
    }
}
