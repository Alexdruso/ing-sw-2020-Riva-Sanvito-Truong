package it.polimi.ingsw.client.ui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.utils.StringCapturedStackTrace;

/**
 * A generic user interface.
 */
public abstract class Ui {
    /**
     * Initialize the user interface.
     */
    public abstract void init();

    /**
     * Gets an instance of a UI-specific ClientState.
     *
     * @param clientState the requested client state
     * @param client      the client
     * @return an instance of a UI-specific ClientState
     */
    public abstract AbstractClientState getClientState(ClientState clientState, Client client);

    /**
     * Displays an error on the user interface.
     *
     * @param message the error message
     */
    public abstract void notifyError(String message);

    /**
     * Displays an error on the user interface.
     *
     * @param ex the exception
     */
    public void notifyError(Exception ex) {
        notifyError(new StringCapturedStackTrace(ex).toString());
    }

    /**
     * Displays an error on the user interface.
     *
     * @param message the error message
     * @param ex      the exception
     */
    public void notifyError(String message, Exception ex) {
        notifyError(String.format("%s\nDettagli:\n", message, new StringCapturedStackTrace(ex).toString()));
    }
}
