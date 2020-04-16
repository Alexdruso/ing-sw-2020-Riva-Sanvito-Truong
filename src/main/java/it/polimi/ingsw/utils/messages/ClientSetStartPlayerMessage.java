package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.controller.User;

public class ClientSetStartPlayerMessage extends ClientMessage {
    public final User startPlayer;

    public ClientSetStartPlayerMessage(User startPlayer) {
        super();
        this.startPlayer = startPlayer;
    }

    /**
     * This method returns the type of the current action
     *
     * @return the type of the current action, as an instance of PlayerActions
     */
    @Override
    public ClientMessages getMessageType() {
        return ClientMessages.SET_START_PLAYER;
    }
}
