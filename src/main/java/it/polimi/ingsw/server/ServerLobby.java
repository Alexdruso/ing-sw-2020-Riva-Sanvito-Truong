package it.polimi.ingsw.server;

import it.polimi.ingsw.utils.networking.Connection;

import java.util.LinkedHashMap;

/**
 * This class represents a single game lobby, to which players may join
 */
public class ServerLobby {
    /**
     * The map containing the (Nickname, Connection) pairs
     */
    private LinkedHashMap<String, Connection> connectedUsers;

    /**
     * The number of players in the lobby
     */
    private int lobbyMaxPlayerCount;

    /**
     * The reference to the server
     */
    private Server server;

    /**
     * The class constructor
     * @param server the Server reference
     */
    public ServerLobby(Server server, int playerCount){
        this.connectedUsers = new LinkedHashMap<String, Connection>();
        this.server= server;
        this.lobbyMaxPlayerCount = playerCount;
    }


    public int getLobbyMaxPlayerCount() {
        return lobbyMaxPlayerCount;
    }

    /**
     * This method is called when a Connection needs to be inserted in a lobby
     * @param username the chosen username
     * @param connection the Connection object
     */
    public void joinLobby(String username, Connection connection){
            connectedUsers.put(username, connection);
            if(connectedUsers.size() > lobbyMaxPlayerCount){
                throw new IllegalStateException("Too many players in the lobby");
            }
    }

    /**
     * This method returns a copy of the connectedUsers map
     * @return the copy of connectedUsers
     */
    public LinkedHashMap<String, Connection> getConnectedUsers(){
        return new LinkedHashMap<String, Connection>(connectedUsers);
    }

}
