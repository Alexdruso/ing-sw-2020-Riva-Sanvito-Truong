package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.model.gods.GodCard;

import java.util.ArrayList;
import java.util.List;

/**
 * This immutable class represents a command to set the gods allowed in a game.
 */
public class ClientChooseGodsMessage extends ClientMessage {
    private final List<GodCard> gods;

    /**
     * Class constructor
     *
     * @param gods the gods allowed in the game
     */
    public ClientChooseGodsMessage(List<GodCard> gods){
       super();
       this.gods = gods;
   }

    /**
     * Gets the gods chosen by the user.
     *
     * @return the gods
     */
    public ArrayList<GodCard> getGods() {
        return new ArrayList<GodCard>(gods);
   }

    /**
     * This method returns the type of the current action
     * @return the type of the current action, as an instance of PlayerActions
     */
    @Override
    public ClientMessages getMessageType() {
        return ClientMessages.CHOOSE_GODS;
    }
}
