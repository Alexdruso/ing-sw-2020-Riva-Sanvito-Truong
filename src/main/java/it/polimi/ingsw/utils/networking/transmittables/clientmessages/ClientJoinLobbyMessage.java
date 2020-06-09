package it.polimi.ingsw.utils.networking.transmittables.clientmessages;

import it.polimi.ingsw.server.ServerConnectionSetupHandler;
import it.polimi.ingsw.server.ServerLobbyBuilder;
import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.ServerHandleable;
import it.polimi.ingsw.utils.networking.transmittables.StatusMessages;

/**
 * This immutable class represents a command given by the player to join a lobby.
 */
public class ClientJoinLobbyMessage implements ClientMessage, ServerHandleable {

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
}
