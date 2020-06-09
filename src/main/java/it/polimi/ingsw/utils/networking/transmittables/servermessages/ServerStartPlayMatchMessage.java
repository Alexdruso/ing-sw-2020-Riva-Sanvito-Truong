package it.polimi.ingsw.utils.networking.transmittables.servermessages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.networking.ClientHandleable;

/**
 * The type Server start play match message.
 */
public class ServerStartPlayMatchMessage implements ServerMessage, ClientHandleable {
    /**
     * Instantiates a new Server start play match message.
     */
    public ServerStartPlayMatchMessage() {
        super();
    }

    @Override
    public boolean handleTransmittable(Client client) {
        return false;
    }
}
