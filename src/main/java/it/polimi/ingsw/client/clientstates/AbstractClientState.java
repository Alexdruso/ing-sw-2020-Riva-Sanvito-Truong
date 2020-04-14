package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.networking.Transmittable;

public abstract class AbstractClientState implements Observer<Transmittable> {
    protected final Client client;

    public AbstractClientState(Client client) {
        this.client = client;
    }

    public abstract void setup();

    /**
     * Function called by the main thread that renders the current state to the UI.
     * This function is the only one allowed to be synchronous with the user input.
     */
    public abstract void render();

    public abstract void notifyUiInteraction();

    public void triggerRender() {
        client.requestRender();
    }

    void handleClientError() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    void handleContinue() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    void handleOk() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    void handleTeapot() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    void handleServerError() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

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
