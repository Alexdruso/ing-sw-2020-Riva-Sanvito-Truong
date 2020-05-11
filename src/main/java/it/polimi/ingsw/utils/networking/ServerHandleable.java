package it.polimi.ingsw.utils.networking;

import it.polimi.ingsw.server.ServerConnectionSetupHandler;

/**
 * This interface represents messages able to interact with the server.
 */
public interface ServerHandleable {

    /**
     * Handles the interaction with the server.
     *
     * @param handler the handler
     * @return true if there were no errors.
     */
    boolean handleTransmittable(ServerConnectionSetupHandler handler);
}
