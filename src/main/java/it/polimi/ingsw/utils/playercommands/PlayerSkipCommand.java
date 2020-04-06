package it.polimi.ingsw.utils.playercommands;

import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.view.View;

public class PlayerSkipCommand extends PlayerCommand {

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
