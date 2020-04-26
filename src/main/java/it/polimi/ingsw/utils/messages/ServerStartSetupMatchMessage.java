package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.utils.networking.ClientHandleable;

public class ServerStartSetupMatchMessage implements ServerMessage, ClientHandleable {
    public final User[] userList;

    public ServerStartSetupMatchMessage(User[] userList){
        super();
        this.userList = userList;
    }

    @Override
    public boolean handleTransmittable(Client handler) {
        handler.moveToState(ClientState.SHOW_GAME_PASSIVE);
        return true;
    }
}
