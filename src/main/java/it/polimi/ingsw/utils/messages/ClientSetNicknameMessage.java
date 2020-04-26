package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.server.ServerConnectionSetupHandler;
import it.polimi.ingsw.server.ServerLobbyBuilder;
import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.ServerHandleable;

/**
 * This immutable class represents a command given by the player to set its nickname.
 */
public class ClientSetNicknameMessage implements ClientMessage, ServerHandleable {
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

    @Override
    public boolean handleTransmittable(ServerConnectionSetupHandler handler) {
        ServerLobbyBuilder lobbyBuilder = handler.getLobbyBuilder();
        Connection connection = handler.getConnection();

        boolean status = lobbyBuilder.registerNickname(nickname, connection);

        if(status){
            connection.send(StatusMessages.OK);
            handler.setNickname(nickname);
        } else {
            connection.send(StatusMessages.CLIENT_ERROR);
        }

        return status;
    }
}
