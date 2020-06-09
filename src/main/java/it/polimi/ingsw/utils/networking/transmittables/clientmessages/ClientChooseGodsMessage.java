package it.polimi.ingsw.utils.networking.transmittables.clientmessages;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.User;
import it.polimi.ingsw.server.view.View;
import it.polimi.ingsw.utils.networking.ControllerHandleable;
import it.polimi.ingsw.utils.networking.transmittables.ReducedGod;

import java.util.ArrayList;
import java.util.List;

/**
 * This immutable class represents a command to set the gods allowed in a game.
 */
public class ClientChooseGodsMessage implements ClientMessage, ControllerHandleable {
    private final List<ReducedGod> gods;

    /**
     * Class constructor
     *
     * @param gods the gods allowed in the game
     */
    public ClientChooseGodsMessage(List<ReducedGod> gods) {
        super();
        this.gods = gods;
    }

    @Override
    public boolean handleTransmittable(Controller handler, View view, User user) {
        handler.dispatchChooseGodsAction(this, view, user);
        return true;
    }

    /**
     * Gets the gods chosen by the user.
     *
     * @return the gods
     */
    public List<ReducedGod> getGods() {
        return new ArrayList<>(gods);
    }
}
