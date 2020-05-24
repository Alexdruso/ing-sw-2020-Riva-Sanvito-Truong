package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.server.ServerConnectionSetupHandler;
import it.polimi.ingsw.utils.networking.ControllerHandleable;
import it.polimi.ingsw.utils.networking.ServerHandleable;
import it.polimi.ingsw.view.View;

public class ClientDisconnectMessage implements DisconnectMessage, ClientMessage, ControllerHandleable, ServerHandleable {
    /**
     * Handles the interaction with the controller.
     *
     * @param handler the controller
     * @param view    the view
     * @param user    the user
     * @return true if there were no errors.
     */
    @Override
    public boolean handleTransmittable(Controller handler, View view, User user) {
        handler.dispatchDrawAction(view, user);
        return true;
    }

    /**
     * Handles the interaction with the server.
     *
     * @param handler the handler
     * @return true if there were no errors.
     */
    @Override
    public boolean handleTransmittable(ServerConnectionSetupHandler handler) {
        return handler.getLobbyBuilder().handleDisconnection(handler.getConnection());
    }
}
