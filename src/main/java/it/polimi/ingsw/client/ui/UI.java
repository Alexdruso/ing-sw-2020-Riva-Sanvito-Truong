package it.polimi.ingsw.client.ui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractClientState;
import it.polimi.ingsw.client.clientstates.AbstractClientTurnState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.clientstates.ClientTurnState;
import it.polimi.ingsw.utils.StringCapturedStackTrace;

/**
 * A generic user interface.
 */
public interface UI {
    /**
     * Initialize the user interface.
     *
     * @param onExit a Runnable that the UI must execute when the user requests to exit the program.
     */
    void init(Runnable onExit);

    /**
     * Gets an instance of a UI-specific ClientState.
     *
     * @param clientState the requested client state
     * @param client      the client
     * @return an instance of a UI-specific ClientState
     */
    AbstractClientState getClientState(ClientState clientState, Client client);

    /**
     *
     * @param clientTurnState the requested client turn state
     * @param client          the client
     * @return an instance of a UI-specific ClientTurnState
     */
    AbstractClientTurnState getClientTurnState(ClientTurnState clientTurnState, Client client);

    /**
     * Displays an error on the user interface.
     *
     * @param message the error message
     */
    void notifyError(String message);

    /**
     * Displays an error on the user interface.
     * Please, be aware that this function is not intended to display debug messages to developers,
     * but to render nicely-formatted error messages to the end user, hoping that they will be useful
     * to her/him in order to identify what she/he did wrong.
     *
     * @param ex the exception
     */
    default void notifyError(Exception ex) {
        notifyError(new StringCapturedStackTrace(ex).toString());
    }

    /**
     * Displays an error on the user interface.
     *
     * @param message the error message
     * @param ex      the exception
     */
    default void notifyError(String message, Exception ex) {
        notifyError(String.format("%s%nDettagli:%n%s", message, new StringCapturedStackTrace(ex).toString()));
    }
}
