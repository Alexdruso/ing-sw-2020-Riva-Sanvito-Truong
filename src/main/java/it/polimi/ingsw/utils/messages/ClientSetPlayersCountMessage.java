package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.server.ServerConnectionSetupHandler;
import it.polimi.ingsw.server.ServerLobbyBuilder;
import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.ServerHandleable;
import it.polimi.ingsw.utils.networking.TransmittableHandler;

/**
 * This immutable class represents a command given by set the players count in a new game.
 */
public class ClientSetPlayersCountMessage extends ClientMessage implements ServerHandleable {
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

    /**
     * This method returns the type of the current action
     * @return the type of the current action, as an instance of PlayerActions
     */
    @Override
    public ClientMessages getMessageType() {
        return ClientMessages.SET_PLAYERS_COUNT;
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
