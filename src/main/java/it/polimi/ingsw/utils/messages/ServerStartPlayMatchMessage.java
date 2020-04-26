package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.utils.networking.ClientHandleable;

public class ServerStartPlayMatchMessage implements ServerMessage, ClientHandleable {
    public final User[] userList;

    public ServerStartPlayMatchMessage(User[] userList){
        this.userList = userList;
    }

    @Override
    public boolean handleTransmittable(Client handler) {
        return false;
    }
}
