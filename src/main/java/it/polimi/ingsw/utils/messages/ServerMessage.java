package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.server.Match;
import it.polimi.ingsw.utils.networking.Transmittable;

public abstract class ServerMessage implements Transmittable {

    public User user;

    /**
     * Class constructor
     */
    protected ServerMessage() {
    }

    protected ServerMessage(User user){
        //Either do this and check for null or use an optional
        this.user = user;
    }

    /**
     * This method returns the type of the current action
     * @return the type of the current action, as an instance of PlayerActions
     */
    public abstract ServerMessages getMessageType();

}
