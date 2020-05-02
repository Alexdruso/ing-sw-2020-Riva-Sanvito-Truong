package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.networking.ClientHandleable;

public class ServerWinGameMessage implements ServerMessage, ClientHandleable {
    public final ReducedUser user;

    public ServerWinGameMessage(ReducedUser user) {
        this.user = user;
    }


    @Override
    public boolean handleTransmittable(Client client) {
        return false;
    }
}
