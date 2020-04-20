package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.utils.networking.ControllerHandleable;
import it.polimi.ingsw.utils.networking.TransmittableHandler;
import it.polimi.ingsw.view.View;

public class ClientSetStartPlayerMessage extends ClientMessage implements ControllerHandleable {
    public final User startPlayer;

    public ClientSetStartPlayerMessage(User startPlayer) {
        super();
        this.startPlayer = startPlayer;
    }

    @Override
    public boolean handleTransmittable(Controller handler, View view, User user) {
        return false;
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
