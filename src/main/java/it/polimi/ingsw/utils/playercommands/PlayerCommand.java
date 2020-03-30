package it.polimi.ingsw.utils.playercommands;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.View;

/**
 * This abstract class must be extended by every object representing an action that the player can perform.
 */
public abstract class PlayerCommand {
    private Player player;
    private View view;
    /**
     * This method retrieves the Player object who requested the execution of the action.
     * @return the Player who requested the execution of the action.
     */
    public Player getPlayer(){
        return player;
    }

    /**
     * This method retrieves the View from which the action request arrived
     * @return the View from which the action request arrived
     */
    public View getView(){
        return view;
    }

    /**
     * This method returns the type of the current action
     * @return the type of the current action, as an instance of PlayerActions
     */
    public abstract PlayerCommands getActionType();
}
