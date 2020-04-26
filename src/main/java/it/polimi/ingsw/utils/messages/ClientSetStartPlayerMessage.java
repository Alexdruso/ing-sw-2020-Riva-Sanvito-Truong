package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.utils.networking.ControllerHandleable;
import it.polimi.ingsw.view.View;

public class ClientSetStartPlayerMessage implements ClientMessage, ControllerHandleable {
    public final User startPlayer;

    public ClientSetStartPlayerMessage(User startPlayer) {
        super();
        this.startPlayer = startPlayer;
    }

    @Override
    public boolean handleTransmittable(Controller handler, View view, User user) {
        return false;
    }
}
