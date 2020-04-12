package it.polimi.ingsw.utils.messages;

/**
 * This immutable class represents a command given by the player to join a lobby.
 */
public class ClientJoinLobbyMessage extends ClientMessage {
    /**
     * Class constructor
     */
    public ClientJoinLobbyMessage(){
       super();
   }

    /**
     * This method returns the type of the current action
     * @return the type of the current action, as an instance of PlayerActions
     */
    @Override
    public ClientMessages getMessageType() {
        return ClientMessages.JOIN_LOBBY;
    }
}
