package it.polimi.ingsw.utils.playercommands;

import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.view.View;

/**
 * This immutable class represents a command given by the player to skip the current stage of the turn.
 */
public class PlayerSkipCommand extends PlayerCommand {
    /**
     * Class constructor
     * @param user the User that authored the command
     * @param view the View from which the command has been received
     */
   public PlayerSkipCommand(User user, View view){
       super(user, view);
   }

    /**
     * This method returns the type of the current action
     * @return the type of the current action, as an instance of PlayerActions
     */
    @Override
    public PlayerCommands getActionType() {
        return PlayerCommands.SKIP;
    }
}
