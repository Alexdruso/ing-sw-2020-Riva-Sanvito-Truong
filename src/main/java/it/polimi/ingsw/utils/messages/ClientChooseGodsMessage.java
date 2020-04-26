package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.model.gods.GodCard;
import it.polimi.ingsw.utils.networking.ControllerHandleable;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * This immutable class represents a command to set the gods allowed in a game.
 */
public class ClientChooseGodsMessage implements ClientMessage, ControllerHandleable {
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

    @Override
    public boolean handleTransmittable(Controller handler, View view, User user) {
        return false;
    }

    /**
     * Gets the gods chosen by the user.
     *
     * @return the gods
     */
    public ArrayList<GodCard> getGods() {
        return new ArrayList<GodCard>(gods);
   }
}
