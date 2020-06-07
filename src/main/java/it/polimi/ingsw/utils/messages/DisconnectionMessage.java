package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.User;
import it.polimi.ingsw.server.ServerConnectionSetupHandler;
import it.polimi.ingsw.utils.networking.ClientHandleable;
import it.polimi.ingsw.utils.networking.ControllerHandleable;
import it.polimi.ingsw.utils.networking.ServerHandleable;
import it.polimi.ingsw.server.view.View;

/**
 * This message handles both client and server disconnections
 */
public class DisconnectionMessage implements ClientMessage, ClientHandleable,
        ServerMessage, ControllerHandleable, ServerHandleable {
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

    /**
     * Handles the interaction with the client.
     *
     * @param client the client
     * @return true if there were no errors
     */
    @Override
    public boolean handleTransmittable(Client client) {
        client.moveToState(ClientState.DISCONNECT);
        return true;
    }
}
