package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.utils.networking.Transmittable;
import it.polimi.ingsw.view.View;

/**
 * This abstract class must be extended by every object representing an action that the player can perform.
 */
public abstract class ClientMessage implements Transmittable {
    /**
     * Class constructor
     */
    protected ClientMessage() {

    }

    /**
     * This method returns the type of the current action
     * @return the type of the current action, as an instance of PlayerActions
     */
    public abstract ClientMessages getMessageType();
}
