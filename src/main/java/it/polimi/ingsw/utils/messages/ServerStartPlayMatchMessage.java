package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.networking.ClientHandleable;

public class ServerStartPlayMatchMessage implements ServerMessage, ClientHandleable {
    public ServerStartPlayMatchMessage(){
        super();
    }

    @Override
    public boolean handleTransmittable(Client client) {
        return false;
    }
}
