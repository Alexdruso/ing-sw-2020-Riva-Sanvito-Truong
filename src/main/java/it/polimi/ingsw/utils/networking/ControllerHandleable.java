package it.polimi.ingsw.utils.networking;


import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.view.View;

public interface ControllerHandleable {
    boolean handleTransmittable(Controller handler, View view, User user);
}
