package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.controller.User;

public class ServerStartPlayMatchMessage extends ServerMessage {
    public final User[] userList;

    public ServerStartPlayMatchMessage(User[] userList){
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
        return ServerMessages.START_MATCH_PLAY;
    }
}
