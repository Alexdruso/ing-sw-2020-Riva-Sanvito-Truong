package it.polimi.ingsw.server;

import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.messages.*;
import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.Transmittable;

import java.util.Optional;

/**
 * This class has the responsibility of handling the first steps of game setup.
 * In particular it handles the request for a nickname and the joining of a lobby for a newly connected
 * client
 */
public class ServerConnectionSetupHandler implements Observer<Transmittable> {
    /**
     * The reference to the server
     */
    private final Server server;
    /**
     * The connection of which this object is Observer and to which it needs to send messages
     */
    private final Connection connection;
    /**
     * The nickname of the player
     */
    private Optional<String> nickname;
    /**
     * The reference to the ServerLobbyBuilder in server
     */
    private ServerLobbyBuilder lobbyBuilder;

    /**
     * The class constructor
     * @param server the Server instance
     * @param connection the Connection instance
     */
    public ServerConnectionSetupHandler(Server server, Connection connection){
        this.server = server;
        this.connection = connection;
        this.lobbyBuilder = server.getLobbyBuilder();
    }

    /**
     * This method is called by the Observable to update the observer
     *
     * @param message the message to be received
     */
    @Override
    public void update(Transmittable message) {
        if(message instanceof ClientSetNicknameMessage) {
            String nickname = ((ClientSetNicknameMessage) message).getNickname();
            boolean status = lobbyBuilder.registerNickname(nickname, connection);
            if(status){
                connection.send(StatusMessages.OK);
                this.nickname = Optional.of(nickname);
            } else {
                connection.send(StatusMessages.CLIENT_ERROR);
            }
        } else if(message instanceof ClientSetPlayersCountMessage) {
            int playerCount = ((ClientSetPlayersCountMessage) message).getPlayersCount();
            boolean status = lobbyBuilder.setLobbyMaxPlayerCount(playerCount, connection);
            if(status){
                connection.send(StatusMessages.OK);
            } else {
                //Player count has already been set
                connection.send(StatusMessages.CLIENT_ERROR);
            }
        } else if(message instanceof ClientJoinLobbyMessage){
            boolean status = lobbyBuilder.handleLobbyRequest(nickname.get(), connection);
            if(!status){
                connection.send(StatusMessages.CLIENT_ERROR);
            }
        } else {
            //Received wrong kind of message
            connection.send(StatusMessages.CLIENT_ERROR);
        }
    }
}
