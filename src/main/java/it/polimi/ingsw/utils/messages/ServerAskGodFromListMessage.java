package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.networking.ClientHandleable;

import java.util.ArrayList;
import java.util.List;

public class ServerAskGodFromListMessage implements ServerMessage, ClientHandleable {
    private ReducedUser user;
    private List<ReducedGod> godList;

    public ServerAskGodFromListMessage(ReducedUser user, List<ReducedGod> godList) {
        this.user = user;
        this.godList = godList;
    }

    public List<ReducedGod> getGodList() {
        return new ArrayList<>(godList);
    }

    @Override
    public boolean handleTransmittable(Client client) {
        return false;
    }
}
