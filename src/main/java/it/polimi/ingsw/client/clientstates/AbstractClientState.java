package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.networking.Transmittable;

/**
 * A generic Client state, to be extended by each state.
 */
public abstract class AbstractClientState implements Observer<Transmittable> {
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
     * Handles a CLIENT_ERROR StatusMessage sent by the server.
     *
     * @throws UnsupportedOperationException if CLIENT_ERROR is not a valid StatusMessage for the current state
     */
    protected void handleClientError() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * Handles a CONTINUE StatusMessage sent by the server.
     *
     * @throws UnsupportedOperationException if CONTINUE is not a valid StatusMessage for the current state
     */
    protected void handleContinue() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * Handles a OK StatusMessage sent by the server.
     *
     * @throws UnsupportedOperationException if OK is not a valid StatusMessage for the current state
     */
    protected void handleOk() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * Handles a TEAPOT StatusMessage sent by the server.
     *
     * @throws UnsupportedOperationException if TEAPOT is not a valid StatusMessage for the current state
     */
    protected void handleTeapot() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * Handles a SERVER_ERROR StatusMessage sent by the server.
     *
     * @throws UnsupportedOperationException if SERVER_ERROR is not a valid StatusMessage for the current state
     */
    protected void handleServerError() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * Handles the messages received from the server.
     *
     * @param message the message received from the server
     */
    public void update(Transmittable message) {
        if (!(message instanceof StatusMessages)) {
            return;
        }

        switch ((StatusMessages) message) {
            case CLIENT_ERROR:
                handleClientError();
                break;
            case CONTINUE:
                handleContinue();
                break;
            case OK:
                handleOk();
                break;
            case TEAPOT:
                handleTeapot();
                break;
            case SERVER_ERROR:
                handleServerError();
                break;
            default:
                throw new IllegalStateException();
        }
    }
}
