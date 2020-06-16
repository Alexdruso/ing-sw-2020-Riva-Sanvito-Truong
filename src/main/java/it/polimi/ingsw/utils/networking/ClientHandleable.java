package it.polimi.ingsw.utils.networking;

import it.polimi.ingsw.client.Client;

/**
 * This interface represents messages able to interact with the client.
 */
public interface ClientHandleable {
    /**
     * Handles the interaction with the client.
     *
     * @param handler the client
     * @return true if there were no errors
     */
    boolean handleTransmittable(Client handler);
}
