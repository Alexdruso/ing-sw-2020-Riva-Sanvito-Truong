package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.server.Match;

public class ServerCurrentUserMessage extends ServerMessage{

    public final User user;

    public ServerCurrentUserMessage(User user){
        super();
        this.user = user;
    }

    /**
     * This method returns the type of the current action
     *
     * @return the type of the current action, as an instance of PlayerActions
     */
    @Override
    public ServerMessages getMessageType() {
        return ServerMessages.CURRENT_USER;
    }
}
