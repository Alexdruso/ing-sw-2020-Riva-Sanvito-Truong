package it.polimi.ingsw.utils.messages;

public class ServerAskStarPlayerMessage extends ServerMessage {
    /**
     * This method returns the type of the current action
     *
     * @return the type of the current action, as an instance of PlayerActions
     */
    @Override
    public ServerMessages getMessageType() {
        return ServerMessages.ASK_START_PLAYER;
    }
}
