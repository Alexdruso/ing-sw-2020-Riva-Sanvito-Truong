package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.networking.transmittables.servermessages.ServerMessage;

/**
 * A generic Client state, to be extended by each state.
 */
public abstract class AbstractClientState {
    /**
     * The Client.
     */
    protected final Client client;

    /**
     * Instantiates a new ClientState.
     *
     * @param client the client
     */
    public AbstractClientState(Client client) {
        this.client = client;
    }

    /**
     * Triggers the operations to perform when entering the current state.
     */
    public abstract void setup();

    /**
     * Triggers the operations to perform when exiting the current state
      */
    public void tearDown(){

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
     * Requests a UI render.
     */
    public void triggerRender() {
        client.requestRender();
    }

    /**
     * Handles a message received from the server.
     *
     * @param message the message from the server
     * @return true if the message has been handled by the current state
     */
    public boolean handleServerMessage(ServerMessage message) {
        return false;
    }

    /**
     * Handles a CLIENT_ERROR StatusMessage sent by the server.
     *
     * @throws UnsupportedOperationException if CLIENT_ERROR is not a valid StatusMessage for the current state
     */
    public void handleClientError() {
        throw new UnsupportedOperationException();
    }

    /**
     * Handles a CONTINUE StatusMessage sent by the server.
     *
     * @throws UnsupportedOperationException if CONTINUE is not a valid StatusMessage for the current state
     */
    public void handleContinue() {
        throw new UnsupportedOperationException();
    }

    /**
     * Handles a OK StatusMessage sent by the server.
     *
     * @throws UnsupportedOperationException if OK is not a valid StatusMessage for the current state
     */
    public void handleOk() {
        throw new UnsupportedOperationException();
    }

    /**
     * Handles a TEAPOT StatusMessage sent by the server.
     *
     * @throws UnsupportedOperationException if TEAPOT is not a valid StatusMessage for the current state
     */
    public void handleTeapot() {
        throw new UnsupportedOperationException();
    }

    /**
     * Handles a SERVER_ERROR StatusMessage sent by the server.
     */
    public void handleServerError() {
        //noinspection SpellCheckingInspection,SpellCheckingInspection
        client.getUI().notifyError("Si e' verificato un errore del server.");
    }

}
