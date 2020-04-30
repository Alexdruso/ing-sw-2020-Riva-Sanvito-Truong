package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.model.gods.GodCard;
import it.polimi.ingsw.utils.networking.ClientHandleable;

public class ServerSetGodMessage implements ServerMessage, ClientHandleable {
    public final GodCard godCard;
    public final ReducedUser user;

    public ServerSetGodMessage(GodCard godCard, ReducedUser user){
        this.godCard = godCard;
        this.user = user;
    }

    @Override
    public boolean handleTransmittable(Client client) {
        return false;
    }
}
