package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.server.ServerConnectionSetupHandler;
import it.polimi.ingsw.server.ServerLobbyBuilder;
import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.ServerHandleable;
import it.polimi.ingsw.utils.networking.TransmittableHandler;

/**
 * This immutable class represents a command given by the player to join a lobby.
 */
public class ClientJoinLobbyMessage extends ClientMessage implements ServerHandleable {

    /**
     * Class constructor
     */
    public ClientJoinLobbyMessage(){
       super();
   }
    @Override

    public boolean handleTransmittable(ServerConnectionSetupHandler handler) {
        ServerLobbyBuilder lobbyBuilder = handler.getLobbyBuilder();
        Connection connection = handler.getConnection();
        String nickname = handler.getNickname();
        boolean status = lobbyBuilder.handleLobbyRequest(nickname, connection);
        if(!status){
            connection.send(StatusMessages.CLIENT_ERROR);
        }
        return status;
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
