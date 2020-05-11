package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.networking.ClientHandleable;

/**
 * The type Server disconnect message.
 */
public class ServerDisconnectMessage implements DisconnectMessage, ServerMessage, ClientHandleable {
    @Override
    public boolean handleTransmittable(Client client) {
        client.disconnect();
        return true;
    }
}
