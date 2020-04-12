package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.model.gods.GodCard;

import java.util.ArrayList;
import java.util.List;

/**
 * This immutable class represents a command to choose a specific god for a player.
 */
public class ClientChooseGodMessage extends ClientMessage {
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
