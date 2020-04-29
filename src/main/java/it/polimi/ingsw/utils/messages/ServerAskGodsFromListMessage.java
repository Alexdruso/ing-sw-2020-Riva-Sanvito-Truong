package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.model.gods.GodCard;
import it.polimi.ingsw.utils.networking.ClientHandleable;

import java.util.ArrayList;
import java.util.List;

public class ServerAskGodsFromListMessage implements ServerMessage, ClientHandleable {
    private User user;
    private List<GodCard> godList;

    public ServerAskGodsFromListMessage(User user, List<GodCard> godList) {
        this.user = user;
        this.godList = godList;
    }

    public List<GodCard> getGodList() {
        return new ArrayList<>(godList);
    }

    @Override
    public boolean handleTransmittable(Client handler) {
        return false;
    }
}
