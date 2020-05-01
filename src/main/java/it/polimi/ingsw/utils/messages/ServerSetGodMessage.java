package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.networking.ClientHandleable;

public class ServerSetGodMessage implements ServerMessage, ClientHandleable {
    public final ReducedGod god;
    public final ReducedUser user;

    public ServerSetGodMessage(ReducedGod god, ReducedUser user) {
        this.god = god;
        this.user = user;
    }

    @Override
    public boolean handleTransmittable(Client client) {
        return false;
    }
}
