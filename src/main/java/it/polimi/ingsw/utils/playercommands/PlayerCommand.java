package it.polimi.ingsw.utils.playercommands;

import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.View;

/**
 * This abstract class must be extended by every object representing an action that the player can perform.
 */
public abstract class PlayerCommand {
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
    protected PlayerCommand(User user, View view) {
        this.user = user;
        this.view = view;
    }

    /**
     * This method returns the type of the current action
     * @return the type of the current action, as an instance of PlayerActions
     */
    public abstract PlayerCommands getActionType();
}
