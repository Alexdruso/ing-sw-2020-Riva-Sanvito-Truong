package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.networking.ClientHandleable;

public class ServerAskStartPlayerMessage implements ServerMessage, ClientHandleable {
    @Override
    public boolean handleTransmittable(Client handler) {
        return false;
    }
}
