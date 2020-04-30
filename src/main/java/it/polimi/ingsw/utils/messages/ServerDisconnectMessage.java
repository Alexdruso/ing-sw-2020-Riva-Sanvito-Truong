package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.networking.ClientHandleable;

public class ServerDisconnectMessage implements ServerMessage, DisconnectMessage, ClientHandleable {
    @Override
    public boolean handleTransmittable(Client client) {
        return false;
    }
}
