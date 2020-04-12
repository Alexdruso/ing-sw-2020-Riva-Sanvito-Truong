package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.server.Match;

public class ClientNicknameMessage extends ClientMessage {

    public final String nickname;

    public ClientNicknameMessage(String nickname){
        this.nickname = nickname;
    }

    /**
     * This method returns the type of the current action
     *
     * @return the type of the current action, as an instance of PlayerActions
     */
    @Override
    public ClientMessages getMessageType() {
        return null;
    }
}
