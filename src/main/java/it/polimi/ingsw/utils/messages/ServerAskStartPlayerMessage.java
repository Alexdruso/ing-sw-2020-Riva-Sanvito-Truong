package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.networking.ClientHandleable;
import it.polimi.ingsw.utils.networking.TransmittableHandler;

public class ServerAskStartPlayerMessage extends ServerMessage implements ClientHandleable {
    /**
     * This method returns the type of the current action
     *
     * @return the type of the current action, as an instance of PlayerActions
     */
    @Override
    public ServerMessages getMessageType() {
        return ServerMessages.ASK_START_PLAYER;
    }

    @Override
    public boolean handleTransmittable(Client handler) {
        return false;
    }
}
