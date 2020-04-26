package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.model.gods.GodCard;
import it.polimi.ingsw.utils.networking.ClientHandleable;

public class ServerSetGodMessage implements ServerMessage, ClientHandleable {
    public final GodCard godCard;
    public final User user;

    public ServerSetGodMessage(GodCard godCard, User user){
        this.godCard = godCard;
        this.user = user;
    }

    @Override
    public boolean handleTransmittable(Client handler) {
        return false;
    }
}
