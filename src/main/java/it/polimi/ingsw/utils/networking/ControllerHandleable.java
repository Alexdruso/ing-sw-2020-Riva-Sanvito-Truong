package it.polimi.ingsw.utils.networking;


import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.view.View;

/**
 * This interface represents messages able to interact with the controller.
 */
public interface ControllerHandleable {

    /**
     * Handles the interaction with the controller.
     *
     * @param handler the controller
     * @param view    the view
     * @param user    the user
     * @return true if there were no errors
     */
    boolean handleTransmittable(Controller handler, View view, User user);
}
