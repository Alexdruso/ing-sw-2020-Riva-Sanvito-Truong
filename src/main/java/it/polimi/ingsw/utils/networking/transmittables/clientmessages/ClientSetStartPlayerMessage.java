package it.polimi.ingsw.utils.networking.transmittables.clientmessages;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.User;
import it.polimi.ingsw.server.view.View;
import it.polimi.ingsw.utils.networking.ControllerHandleable;
import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;

/**
 * The type Client set start player message.
 */
public class ClientSetStartPlayerMessage implements ClientMessage, ControllerHandleable {
    /**
     * The player who will start the actual game.
     */
    public final ReducedUser startPlayer;

    /**
     * Instantiates a new Client set start player message.
     *
     * @param startPlayer the start player
     */
    public ClientSetStartPlayerMessage(ReducedUser startPlayer) {
        super();
        this.startPlayer = startPlayer;
    }

    @Override
    public boolean handleTransmittable(Controller handler, View view, User user) {
        handler.dispatchSetStartPlayerAction(this, view, user);
        return true;
    }
}
