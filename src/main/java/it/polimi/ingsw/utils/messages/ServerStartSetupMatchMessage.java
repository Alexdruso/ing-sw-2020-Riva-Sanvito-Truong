package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.utils.networking.ClientHandleable;

public class ServerStartSetupMatchMessage implements ServerMessage, ClientHandleable {
    public final ReducedUser[] userList;

    public ServerStartSetupMatchMessage(User[] userList){
        super();
        this.userList = new ReducedUser[userList.length];
        for (int i = 0; i < userList.length; i++) {
            this.userList[i] = userList[i].toReducedUser();
        }
    }

    @Override
    public boolean handleTransmittable(Client client) {
        client.createGame(userList);
        return true;
    }
}
