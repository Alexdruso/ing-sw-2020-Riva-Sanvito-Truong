package it.polimi.ingsw.model.playeractions;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.View;

/**
 * This abstract class must be extended by every object representing an action that the player can perform.
 */
public abstract class PlayerAction {
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
}
