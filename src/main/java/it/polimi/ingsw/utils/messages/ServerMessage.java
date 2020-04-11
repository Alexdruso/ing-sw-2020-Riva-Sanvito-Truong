package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.server.Match;

public abstract class ServerMessage {

    public final Match match;

    public User user;

    /**
     * Class constructor
     * @param match the Match object from which the message has been sent
     */
    protected ServerMessage(Match match) {
        this.match = match;
    }

    public void setUser(User user){
        //Either do this and check for null or use an optional
        this.user = user;
    }

    /**
     * This method returns the type of the current action
     * @return the type of the current action, as an instance of PlayerActions
     */
    public abstract ServerMessages getMessageType();

}
