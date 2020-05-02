package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.utils.networking.ControllerHandleable;
import it.polimi.ingsw.view.View;

public class ClientDisconnectMessage implements DisconnectMessage, ClientMessage, ControllerHandleable {
    @Override
    public boolean handleTransmittable(Controller handler, View view, User user) {
        handler.dispatchDrawAction(view, user);
        return true;
    }
}
