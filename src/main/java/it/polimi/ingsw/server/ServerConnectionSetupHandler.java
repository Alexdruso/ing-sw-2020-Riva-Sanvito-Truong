package it.polimi.ingsw.server;

import it.polimi.ingsw.observer.LambdaObserver;
import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.ServerHandleable;
import it.polimi.ingsw.utils.networking.Transmittable;
import it.polimi.ingsw.utils.networking.TransmittableHandler;

/**
 * This class has the responsibility of handling the first steps of game setup.
 * In particular it handles the request for a nickname and the joining of a lobby for a newly connected
 * client
 */
public class ServerConnectionSetupHandler implements LambdaObserver, TransmittableHandler {
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
    private String nickname;

    /**
     * The class constructor
     * @param server the Server instance
     * @param connection the Connection instance
     */
    public ServerConnectionSetupHandler(Server server, Connection connection){
        this.server = server;
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public ServerLobbyBuilder getLobbyBuilder() {
        return server.getLobbyBuilder();
    }


    /**
     * This method is called by the Observable to update the observer
     *
     * @param message the message to be received
     */
    public void update(Transmittable message) {
        ((ServerHandleable)message).handleTransmittable(this);
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return this.nickname;
    }
}
