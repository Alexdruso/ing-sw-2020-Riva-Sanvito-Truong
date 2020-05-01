package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.networking.ClientHandleable;

public class ServerAskStartPlayerMessage implements ServerMessage, ClientHandleable {
    ReducedUser user;

    public ServerAskStartPlayerMessage(ReducedUser user) {
        this.user = user;
    }

    @Override
    public boolean handleTransmittable(Client client) {
        return false;
    }
}
