package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.utils.networking.ClientHandleable;

import java.util.ArrayList;
import java.util.List;

public class ServerAskGodsFromListMessage implements ServerMessage, ClientHandleable {
    private final ReducedUser user;
    private final List<ReducedGod> godsList;

    public ServerAskGodsFromListMessage(ReducedUser user, List<ReducedGod> godsList) {
        this.user = user;
        this.godsList = godsList;
    }

    public List<ReducedGod> getGodsList() {
        return new ArrayList<>(godsList);
    }

    @Override
    public boolean handleTransmittable(Client client) {
        client.setCurrentActiveUser(user);
        client.setGods(godsList);
        client.moveToState(ClientState.ASK_GODS_FROM_LIST);
        return true;
    }
}
