package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.utils.networking.ClientHandleable;

public class ServerAskStartPlayerMessage implements ServerMessage, ClientHandleable {
    ReducedUser user;

    public ServerAskStartPlayerMessage(ReducedUser user) {
        this.user = user;
    }

    @Override
    public boolean handleTransmittable(Client client) {
        client.setCurrentActiveUser(user);
        client.moveToState(ClientState.ASK_START_PLAYER);
        return true;
    }
}
