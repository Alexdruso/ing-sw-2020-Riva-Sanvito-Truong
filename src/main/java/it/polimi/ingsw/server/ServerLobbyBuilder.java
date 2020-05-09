package it.polimi.ingsw.server;

import it.polimi.ingsw.config.ConfigParser;
import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.networking.Connection;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents a single game lobby, to which players may join
 */
public class ServerLobbyBuilder {
    private static final Logger LOGGER = Logger.getLogger(ServerLobbyBuilder.class.getName());

    /**
     * The reference to the server
     */
    private final Server server;
    /**
     * The connection that arrived first and who has control of the player count for the lobby
     */
    private Connection firstConnection;
    /**
     * A Map containing all connections and the relative nicknames
     */
    private final Map<Connection, String> registeredNicknames;
    /**
     * A LinkedList containing players who requested to be inserted in a lobby, in order of arrival
     */
    private final LinkedList<Connection> lobbyRequestingConnections;
    /**
     * The maximum number of players for the current lobby
     */
    private int currentLobbyPlayerCount;
    /**
     * The lock used to synchronize over currentLobbyPlayerCount
     */
    private final Object playerCountLock;
    /**
     * The current status of the LobbyBuilder Thread
     */
    private volatile boolean active;

    /**
     * The class constructor
     * @param server the Server reference
     */
    public ServerLobbyBuilder(Server server){
        this.server= server;
        this.registeredNicknames = new ConcurrentHashMap<>();
        this.lobbyRequestingConnections = new LinkedList<>();
        this.active = true;
        this.playerCountLock = new Object();
    }

    /**
     * This method accepts a nickname and a connection and, if both are valid, registers the nickname, along with the
     * connection
     * @param nickname a String representing the nickname of the user
     * @param connection the Connection from which the user is communicating
     * @return true if the registering has been successful, false otherwise
     */
    public boolean registerNickname(String nickname, Connection connection) {
        synchronized (registeredNicknames) {
            if (registeredNicknames.containsValue(nickname) || registeredNicknames.containsKey(connection)) {
                return false;
            } else {
                registeredNicknames.put(connection, nickname);
                return true;
            }
        }
    }

    /**
     * This method removes the nickname from the registeredNicknames structure
     *
     * @param nickname the nickname that has to be removed
     */
    public void removeNickname(String nickname) {
        synchronized (registeredNicknames) {
            registeredNicknames.remove(nickname);
        }
    }

    /**
     * This method accepts a playerCount and a connection and, if both are valid, sets the maximum player count
     * This method also notifies all threads that are synchronized with playerCountLock
     *
     * @param playerCount an int representing the maximum number of players to allow in the lobby
     * @param connection  the Connection from which the user is communicating
     * @return true if the count has been set correctly, false otherwise
     */
    public boolean setLobbyMaxPlayerCount(int playerCount, Connection connection) {
        if (firstConnection == null || connection != firstConnection) {
            return false;
        }

        ConfigParser configParser = ConfigParser.getInstance();
        int MIN_PLAYERS_PER_GAME = configParser.getIntProperty("minPlayersPerGame");
        int MAX_PLAYERS_PER_GAME = configParser.getIntProperty("maxPlayersPerGame");

        if(playerCount > MAX_PLAYERS_PER_GAME || playerCount < MIN_PLAYERS_PER_GAME){
            return false;
        }

        synchronized(playerCountLock){
            if(currentLobbyPlayerCount != 0){
                return false;
            }
            currentLobbyPlayerCount = playerCount;
            playerCountLock.notifyAll();
        }
        return true;
    }

    /**
     * This method retrieves the maximum number of players allowed in the lobby.
     * If the player count has not been set, it returns 0
     * @return the maximum number of players allowed in the lobby
     */
    public int getCurrentLobbyPlayerCount(){
        return currentLobbyPlayerCount;
    }

    /**
     * This method handles a request to join the lobby by a player. If nickname and connection are both valid,
     * the method adds the user to the queue, notifies all waiting threads and sends an OK on the connection
     * @param nickname a String representing the nickname of the user is communicating
     * @param connection the Connection from which the user is
     */
    public boolean handleLobbyRequest(String nickname, Connection connection){
        synchronized(registeredNicknames){
            if(!registeredNicknames.containsValue(nickname) || !registeredNicknames.containsKey(connection)){
                return false;
            }
        }
        synchronized(lobbyRequestingConnections){
            lobbyRequestingConnections.add(connection);
            lobbyRequestingConnections.notifyAll();
        }
        return true;
    }

    /**
     * This method begins the main ServerLobbyBuilder thread, waiting for lobby requests and handling them
     * in order to generate a Match.
     */
    public void start(){
        while(active){
            synchronized(lobbyRequestingConnections){
                while(lobbyRequestingConnections.size() == 0){
                    try{
                        lobbyRequestingConnections.wait();
                    } catch (InterruptedException e){
                        LOGGER.log(Level.FINE, "Interrupting thread following InterruptedException", e);
                        Thread.currentThread().interrupt();
                    }
                }
                firstConnection = lobbyRequestingConnections.getFirst();
            }
            synchronized(playerCountLock) {
                currentLobbyPlayerCount = 0;
                firstConnection.send(StatusMessages.CONTINUE);
                while (currentLobbyPlayerCount == 0) {
                    try {
                        playerCountLock.wait();
                    } catch (InterruptedException e) {
                        LOGGER.log(Level.FINE, "Interrupting thread following InterruptedException", e);
                        Thread.currentThread().interrupt();
                    }
                }
            }
            synchronized(lobbyRequestingConnections){
                while(lobbyRequestingConnections.size() < currentLobbyPlayerCount){
                    try {
                        lobbyRequestingConnections.wait();
                    } catch (InterruptedException e) {
                        LOGGER.log(Level.FINE, "Interrupting thread following InterruptedException", e);
                        Thread.currentThread().interrupt();
                    }
                }
            }

            Match match = new Match(server);

            for(int i = 0; i < currentLobbyPlayerCount; i++){
                Connection connection = lobbyRequestingConnections.removeFirst();
                String nickname = registeredNicknames.get(connection);
                match.addParticipant(nickname, connection);
            }
            server.submitMatch(match);
        }
    }

    /**
     * This method stops the main ServerLobbyBuilder thread
     */
    public void stop(){
        active = false;
    }
}
