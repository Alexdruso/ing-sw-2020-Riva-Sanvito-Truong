package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.utils.networking.ClientHandleable;

//TODO: REMOVE ME!!!
public class ServerSetCurrentUserMessage implements ServerMessage, ClientHandleable {
    public final User user;

    public ServerSetCurrentUserMessage(User user){
        this.user = user;
    }

    @Override
    public boolean handleTransmittable(Client handler) {
        return false;
    }
}
