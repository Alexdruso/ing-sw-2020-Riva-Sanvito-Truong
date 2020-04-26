package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.server.ServerConnectionSetupHandler;
import it.polimi.ingsw.utils.networking.ControllerHandleable;
import it.polimi.ingsw.utils.networking.ServerHandleable;
import it.polimi.ingsw.view.View;

public class ClientDisconnectMessage implements ClientMessage, DisconnectMessage, ControllerHandleable, ServerHandleable {
    @Override
    public boolean handleTransmittable(Controller handler, View view, User user) {
        return false;
    }

    @Override
    public boolean handleTransmittable(ServerConnectionSetupHandler handler) {
        return false;
    }
}
