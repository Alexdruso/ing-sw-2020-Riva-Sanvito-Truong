package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.server.Match;

public class ServerStartSetupMatchMessage extends ServerMessage{

    public final User[] userList;

    public ServerStartSetupMatchMessage(User[] userList){
        super();
        this.userList = userList;
    }

    /**
     * This method returns the type of the current action
     *
     * @return the type of the current action, as an instance of PlayerActions
     */
    @Override
    public ServerMessages getMessageType() {
        return ServerMessages.START_MATCH_SETUP;
    }
}
