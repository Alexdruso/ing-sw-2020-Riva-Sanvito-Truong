package it.polimi.ingsw.server;

import it.polimi.ingsw.config.ConfigParser;
import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.networking.Connection;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

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

    private Optional<String> firstUsername;
    private Optional<Connection> firstConnection;

    private final int MIN_PLAYERS_PER_GAME;
    private final int MAX_PLAYERS_PER_GAME;

    private final Object lock;

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
        this.lobbyMaxPlayerCount = 0;
        this.firstUsername = Optional.empty();
        this.firstConnection = Optional.empty();
        lock = new Object();
    }


    public int getLobbyMaxPlayerCount() {
        synchronized(lock){
            return lobbyMaxPlayerCount;
        }
    }

    /**
     * This method is called when the first player gives information about the lobby size
     * @param playerCount the number of players that need to join before the game can begin
     * @return true if the playerCount has been set correctly, false otherwise
     */
    public boolean setLobbyMaxPlayerCount(int playerCount, String username, Connection connection){
        if(firstConnection.isEmpty() || firstUsername.isEmpty()){
            return false;
        }
        if(!username.equals(firstUsername.get()) || !connection.equals(firstConnection.get())){
            return false;
        }
        synchronized (lock){
            if(lobbyMaxPlayerCount == 0 && playerCount >= MIN_PLAYERS_PER_GAME && playerCount <= MAX_PLAYERS_PER_GAME){
                lobbyMaxPlayerCount = playerCount;
                lock.notifyAll();
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * This method is called when a Connection needs to be inserted in a lobby
     * @param username the chosen username
     * @param connection the Connection object
     */
    public void joinLobby(String username, Connection connection){
        synchronized(lock){
            if(lobbyMaxPlayerCount == 0){
                if(firstUsername.isEmpty()) {
                    firstUsername = Optional.of(username);
                    firstConnection = Optional.of(connection);
                    connection.send(StatusMessages.CONTINUE); //Ask for the player count
                }
                while (lobbyMaxPlayerCount == 0) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            connectedUsers.put(username, connection);

            if(connectedUsers.size() == lobbyMaxPlayerCount){
                server.createMatch(this);
            }
            connection.send(StatusMessages.OK);
        }
    }

    /**
     * This method returns a copy of the connectedUsers map
     * @return the copy of connectedUsers
     */
    public LinkedHashMap<String, Connection> getConnectedUsers(){
        synchronized (lock){
            return new LinkedHashMap<String, Connection>(connectedUsers);
        }
    }

}
