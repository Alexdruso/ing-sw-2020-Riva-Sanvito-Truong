package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.networking.ClientHandleable;

public class ServerLoseGameMessage implements ServerMessage, ClientHandleable {
    public final ReducedUser user;

    public ServerLoseGameMessage(ReducedUser user) {
        this.user = user;
    }

    @Override
    public boolean handleTransmittable(Client client) {
        return false;
    }
}
