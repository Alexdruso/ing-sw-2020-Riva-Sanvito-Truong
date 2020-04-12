package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.view.View;

/**
 * This immutable class represents a command given by the player to skip the current stage of the turn.
 */
public class ClientSkipMessage extends ClientMessage {
    /**
     * Class constructor
     */
   public ClientSkipMessage(){
       super();
   }

    /**
     * This method returns the type of the current action
     * @return the type of the current action, as an instance of PlayerActions
     */
    @Override
    public ClientMessages getMessageType() {
        return ClientMessages.SKIP;
    }
}