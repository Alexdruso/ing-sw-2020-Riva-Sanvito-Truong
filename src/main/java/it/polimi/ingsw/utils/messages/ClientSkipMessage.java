package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.utils.networking.ControllerHandleable;
import it.polimi.ingsw.view.View;

/**
 * This immutable class represents a command given by the player to skip the current stage of the turn.
 */
public class ClientSkipMessage implements ClientMessage, ControllerHandleable{
    /**
     * Class constructor
     */
   public ClientSkipMessage(){
       super();
   }

   @Override
    public boolean handleTransmittable(Controller handler, View view, User user) {
        handler.dispatchSkipAction(this, view, user);
        return true;
    }
}
