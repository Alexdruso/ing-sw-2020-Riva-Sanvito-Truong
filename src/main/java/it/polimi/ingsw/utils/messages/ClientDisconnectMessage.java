package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.utils.networking.ControllerHandleable;
import it.polimi.ingsw.view.View;

public class ClientDisconnectMessage implements ClientMessage, DisconnectMessage, ControllerHandleable {
    @Override
    public boolean handleTransmittable(Controller handler, View view, User user) {
        handler.dispatchSkipAction(view, user);
        return true;
    }
}
