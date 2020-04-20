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
     * The flag to indicate whether the connection has been inserted into a lobby or not
     */
    private boolean hasJoinedLobby;

    private ServerLobbyBuilder lobbyBuilder;

    /**
     * The class constructor
     * @param server the Server instance
     * @param connection the Connection instance
     */
    public ServerConnectionSetupHandler(Server server, Connection connection){
        this.server = server;
        this.connection = connection;
        this.hasJoinedLobby = false;
        this.lobbyBuilder = server.getLobbyBuilder();
    }

    /**
     * This method is called by the Observable to update the observer
     *
     * @param message the message to be received
     */
    @Override
    public void update(Transmittable message) {
        //TODO: This lines needs to become if(!message.getMessageType != ClientMessages.NICKNAME)
        //This implies that the function getMessageType is moved from ClientMessage and ServerMessage to Transmittable
        if(message instanceof ClientSetNicknameMessage) {
            String nickname = ((ClientSetNicknameMessage) message).getNickname();
            boolean status = lobbyBuilder.registerNickname(nickname, connection);
            if(status){
                this.nickname = Optional.of(nickname);
                connection.send(StatusMessages.OK);
            } else {
                connection.send(StatusMessages.CLIENT_ERROR);
            }
        } else if(message instanceof ClientSetPlayersCountMessage) {
            if(hasJoinedLobby && nickname.isPresent()){
                int playerCount = ((ClientSetPlayersCountMessage) message).getPlayersCount();
                boolean status = lobbyBuilder.setLobbyMaxPlayerCount(playerCount, connection);
                if(status){
                    connection.send(StatusMessages.OK);
                } else {
                    //Player count has already been set
                    connection.send(StatusMessages.CLIENT_ERROR);
                }
            } else {
                //Client trying to set player count before being asked to
                connection.send(StatusMessages.CLIENT_ERROR);
            }
        } else if(message instanceof ClientJoinLobbyMessage){
            if(nickname.isEmpty()){
                connection.send(StatusMessages.CLIENT_ERROR);
            } else {
                hasJoinedLobby = true;
                lobbyBuilder.handleLobbyRequest(nickname.get(), connection);
                //connection.removeObserver(this); //From now on, the connection is to be handled by the Match
            }
        } else {
            //Received wrong kind of message
            connection.send(StatusMessages.CLIENT_ERROR);
        }
    }
}
