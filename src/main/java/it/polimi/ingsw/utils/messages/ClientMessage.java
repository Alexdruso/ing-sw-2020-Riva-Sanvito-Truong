package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.utils.networking.Transmittable;
import it.polimi.ingsw.view.View;

/**
 * This abstract class must be extended by every object representing an action that the player can perform.
 */
public abstract class ClientMessage implements Transmittable {
    /**
     * The User that authored the command
     */
    public final User user;

    /**
     * The View from which the command has been received
     */
    public final View view;

    /**
     * Class constructor
     * @param user the User that authored the command
     * @param view the View from which the command has been received
     */
    protected ClientMessage(User user, View view) {
        this.user = user;
        this.view = view;
    }

    /**
     * This method returns the type of the current action
     * @return the type of the current action, as an instance of PlayerActions
     */
    public abstract ClientMessages getMessageType();
}
