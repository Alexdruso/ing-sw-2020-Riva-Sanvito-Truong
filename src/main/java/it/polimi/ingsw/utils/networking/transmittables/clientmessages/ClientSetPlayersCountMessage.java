package it.polimi.ingsw.utils.networking.transmittables.clientmessages;

import it.polimi.ingsw.server.ServerConnectionSetupHandler;
import it.polimi.ingsw.server.ServerLobbyBuilder;
import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.ServerHandleable;
import it.polimi.ingsw.utils.networking.transmittables.StatusMessages;

/**
 * This immutable class represents a command given by set the players count in a new game.
 */
public class ClientSetPlayersCountMessage implements ClientMessage, ServerHandleable {
    private final int playersCount;

    /**
     * Class constructor
     *
     * @param playersCount the players count
     */
    public ClientSetPlayersCountMessage(int playersCount){
       super();
       this.playersCount = playersCount;
   }

    /**
     * Gets the players count chosen by the user.
     *
     * @return the players count
     */
    public int getPlayersCount() {
        return playersCount;
   }

    @Override
    public boolean handleTransmittable(ServerConnectionSetupHandler handler) {
        ServerLobbyBuilder lobbyBuilder = handler.getLobbyBuilder();
        Connection connection = handler.getConnection();

        boolean status = lobbyBuilder.setLobbyMaxPlayerCount(playersCount, connection);
        if(status){
            connection.send(StatusMessages.OK);
        } else {
            //Player count has already been set
            connection.send(StatusMessages.CLIENT_ERROR);
        }
        return status;
    }
}
