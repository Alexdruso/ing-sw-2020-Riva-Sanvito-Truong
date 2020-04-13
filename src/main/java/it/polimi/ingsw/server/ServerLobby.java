package it.polimi.ingsw.server;

import it.polimi.ingsw.config.ConfigParser;
import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.networking.Connection;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class represents a single game lobby, to which players may join
 */
public class ServerLobby {
    /**
     * The map containing the (Nickname, Connection) pairs
     */
    private Map<String, Connection> connectedUsers;

    /**
     * The number of players in the lobby
     */
    private int lobbyPlayerCount; //Note: not using an Optional here since it is highly discouraged to synchronize on an optional

    /**
     * The reference to the server
     */
    private Server server;

    private final int MIN_PLAYERS_PER_GAME;
    private final int MAX_PLAYERS_PER_GAME;

    /**
     * The class constructor
     * @param server the Server reference
     */
    public ServerLobby(Server server){
        ConfigParser configParser = ConfigParser.getInstance();
        MIN_PLAYERS_PER_GAME = Integer.parseInt(configParser.getProperty("minPlayersPerGame"));
        MAX_PLAYERS_PER_GAME = Integer.parseInt(configParser.getProperty("maxPlayersPerGame"));
        this.connectedUsers = new LinkedHashMap<String, Connection>();
        this.server= server;
        this.lobbyPlayerCount = 0;
    }

    /**
     * This method is called when the first player gives information about the lobby size
     * @param playerCount the number of players that need to join before the game can begin
     * @return true if the playerCount has been set correctly, false otherwise
     */
    public boolean setPlayerCount(int playerCount){
        if(lobbyPlayerCount == 0 && playerCount >= MIN_PLAYERS_PER_GAME && playerCount <= MAX_PLAYERS_PER_GAME){
            lobbyPlayerCount = playerCount;
            notifyAll();
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method is called when a Connection needs to be inserted in a lobby
     * @param username the chosen username
     * @param connection the Connection object
     */
    public void joinLobby(String username, Connection connection){
        if(lobbyPlayerCount == 0) {
            connection.send(StatusMessages.CONTINUE); //Ask for the player count
            while (lobbyPlayerCount == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        connectedUsers.put(username, connection);
        if(connectedUsers.size() == lobbyPlayerCount){
           server.createMatch(this);
        }
    }

    /**
     * This method returns a copy of the connectedUsers map
     * @return the copy of connectedUsers
     */
    public Map<String, Connection> getConnectedUsers(){
        return new LinkedHashMap<String, Connection>(connectedUsers);
    }

}
