package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.view.View;

/**
 * This immutable class represents a command given by the player to set its nickname.
 */
public class ClientSetNicknameMessage extends ClientMessage {
    private final String nickname;

    /**
     * Class constructor
     *
     * @param nickname the nickname
     */
    public ClientSetNicknameMessage(String nickname){
       super();
       this.nickname = nickname;
   }

    /**
     * Gets the nickname chosen by the user.
     *
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
   }

    /**
     * This method returns the type of the current action
     * @return the type of the current action, as an instance of PlayerActions
     */
    @Override
    public ClientMessages getMessageType() {
        return ClientMessages.SET_NICKNAME;
    }
}
