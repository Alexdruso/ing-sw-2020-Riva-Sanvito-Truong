package it.polimi.ingsw.server;

import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.messages.*;
import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.Transmittable;

import java.util.Optional;

public class ServerConnectionSetupHandler implements Observer<Transmittable> {

    private final Server server;
    private final Connection connection;
    private Optional<String> nickname;
    private boolean hasJoinedLobby;

    public ServerConnectionSetupHandler(Server server, Connection connection){
        this.server = server;
        this.connection = connection;
        this.hasJoinedLobby = false;
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
            this.nickname = Optional.of(nickname);
            connection.send(StatusMessages.OK);
        } else if(message instanceof ClientSetPlayersCountMessage) {
            if(hasJoinedLobby){
                int playerCount = ((ClientSetPlayersCountMessage) message).getPlayersCount();
                boolean status = server.setPlayerCount(playerCount);
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
                server.joinLobby(nickname.get(), connection);
                hasJoinedLobby = true;
                connection.removeObserver(this); //From now on, the connection is to be handled by the Matc
            }
        } else {
            //Received wrong kind of message
            connection.send(StatusMessages.CLIENT_ERROR);
        }
    }
}
