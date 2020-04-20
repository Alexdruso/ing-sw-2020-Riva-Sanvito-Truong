package it.polimi.ingsw.server;

import it.polimi.ingsw.config.ConfigParser;
import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.networking.Connection;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class represents a single game lobby, to which players may join
 */
public class ServerLobbyBuilder {
    /**
     * The reference to the server
     */
    private Server server;
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

    private boolean active;

    private final Object playerCountLock;

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

    boolean registerNickname(String nickname, Connection connection){
        synchronized(registeredNicknames){
            if(registeredNicknames.containsValue(nickname)){
                return false;
            } else {
                registeredNicknames.put(connection, nickname);
                return true;
            }
        }
    }

    public boolean setLobbyMaxPlayerCount(int playerCount, Connection connection){
        if(firstConnection == null || connection != firstConnection){
            return false;
        }

        ConfigParser configParser = ConfigParser.getInstance();
        int MIN_PLAYERS_PER_GAME = Integer.parseInt(configParser.getProperty("minPlayersPerGame"));
        int MAX_PLAYERS_PER_GAME = Integer.parseInt(configParser.getProperty("maxPlayersPerGame"));

        if(playerCount > MAX_PLAYERS_PER_GAME || playerCount < MIN_PLAYERS_PER_GAME){
            return false;
        }

        synchronized(playerCountLock){
            if(currentLobbyPlayerCount != 0){
                return false;
            }
            currentLobbyPlayerCount = playerCount;
            playerCountLock.notify();
        }
        return true;
    }

    public int getCurrentLobbyPlayerCount(){
        return currentLobbyPlayerCount;
    }

    public void handleLobbyRequest(String nickname, Connection connection){
        synchronized(lobbyRequestingConnections){
            lobbyRequestingConnections.add(connection);
            lobbyRequestingConnections.notify();
            connection.send(StatusMessages.OK);
        }
    }

    public void start(){
        while(active){
            synchronized(lobbyRequestingConnections){
                while(lobbyRequestingConnections.size() == 0){
                    try{
                        lobbyRequestingConnections.wait();
                    } catch (InterruptedException ignored){ }
                }
                firstConnection = lobbyRequestingConnections.getFirst();
            }
            synchronized(playerCountLock) {
                currentLobbyPlayerCount = 0;
                firstConnection.send(StatusMessages.CONTINUE);
                while (currentLobbyPlayerCount == 0) {
                    try {
                        playerCountLock.wait();
                    } catch (InterruptedException ignored) {
                    }
                }
            }
            synchronized(lobbyRequestingConnections){
                while(lobbyRequestingConnections.size() < currentLobbyPlayerCount){
                    try {
                        lobbyRequestingConnections.wait();
                    } catch (InterruptedException ignored) { }
                }
            }

            Match match = new Match();

            for(int i = 0; i < currentLobbyPlayerCount; i++){
                Connection connection = lobbyRequestingConnections.removeFirst();
                String nickname = registeredNicknames.get(connection);
                match.addParticipant(nickname, connection);
            }
            server.submitMatch(match);
        }
    }

}
