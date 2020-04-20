package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.utils.networking.ClientHandleable;
import it.polimi.ingsw.utils.networking.TransmittableHandler;

public class ServerStartPlayMatchMessage extends ServerMessage implements ClientHandleable {
    public final User[] userList;

    public ServerStartPlayMatchMessage(User[] userList){
        super();
        this.userList = userList;
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
        return ServerMessages.START_MATCH_PLAY;
    }
}
