package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.model.gods.GodCard;
import it.polimi.ingsw.server.Match;
import it.polimi.ingsw.utils.networking.ClientHandleable;
import it.polimi.ingsw.utils.networking.TransmittableHandler;

public class ServerSetGodMessage extends ServerMessage implements ClientHandleable {

    public final GodCard godCard;
    public final User user;

    public ServerSetGodMessage(GodCard godCard, User user){
        super();
        this.godCard = godCard;
        this.user = user;
    }

    @Override
    public boolean handleTransmittable(Client handler) {
        return false;
    }

    /**
     * This method returns the type of the current action
     *
     * @return the type of the current action, as an instance of PlayerActions
     */
    @Override
    public ServerMessages getMessageType() {
        return ServerMessages.SET_GOD;
    }
}
