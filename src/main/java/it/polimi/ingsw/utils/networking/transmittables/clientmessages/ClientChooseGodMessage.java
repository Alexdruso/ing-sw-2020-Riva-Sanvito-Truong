package it.polimi.ingsw.utils.networking.transmittables.clientmessages;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.User;
import it.polimi.ingsw.server.view.View;
import it.polimi.ingsw.utils.networking.ControllerHandleable;
import it.polimi.ingsw.utils.networking.transmittables.ReducedGod;

/**
 * This immutable class represents a command to choose a specific god for a player.
 */
public class ClientChooseGodMessage implements ClientMessage, ControllerHandleable {
    private final ReducedGod god;

    /**
     * Class constructor
     *
     * @param god the god requested by the user
     */
    public ClientChooseGodMessage(ReducedGod god) {
        super();
        this.god = god;
    }

    @Override
    public boolean handleTransmittable(Controller handler, View view, User user) {
        handler.dispatchChooseGodAction(this, view, user);
        return true;
    }

    /**
     * Gets the god chosen by the user.
     *
     * @return the god
     */
    public ReducedGod getGod() {
        return god;
    }
}
