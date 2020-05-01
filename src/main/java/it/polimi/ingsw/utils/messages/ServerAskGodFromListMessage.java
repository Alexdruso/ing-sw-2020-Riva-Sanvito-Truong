package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.gods.GodCard;
import it.polimi.ingsw.utils.networking.ClientHandleable;

import java.util.ArrayList;
import java.util.List;

public class ServerAskGodFromListMessage implements ServerMessage, ClientHandleable {
    private ReducedUser user;
    private List<GodCard> godList;

    public ServerAskGodFromListMessage(ReducedUser user, List<GodCard> godList) {
        this.user = user;
        this.godList = godList;
    }

    public List<GodCard> getGodList() {
        return new ArrayList<>(godList);
    }

    @Override
    public boolean handleTransmittable(Client client) {
        return false;
    }
}
