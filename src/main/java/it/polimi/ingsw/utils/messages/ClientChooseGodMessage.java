package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.model.gods.GodCard;
import it.polimi.ingsw.utils.networking.ControllerHandleable;
import it.polimi.ingsw.utils.networking.TransmittableHandler;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * This immutable class represents a command to choose a specific god for a player.
 */
public class ClientChooseGodMessage extends ClientMessage implements ControllerHandleable {
    private final GodCard god;

    /**
     * Class constructor
     *
     * @param god the god requested by the user
     */
    public ClientChooseGodMessage(GodCard god){
       super();
       this.god = god;
   }

    @Override
    public boolean handleTransmittable(Controller handler, View view, User user) {
        return false;
    }

    /**
     * Gets the god chosen by the user.
     *
     * @return the god
     */
    public GodCard getGod() {
        return god;
   }

    /**
     * This method returns the type of the current action
     * @return the type of the current action, as an instance of PlayerActions
     */
    @Override
    public ClientMessages getMessageType() {
        return ClientMessages.CHOOSE_GOD;
    }
}
