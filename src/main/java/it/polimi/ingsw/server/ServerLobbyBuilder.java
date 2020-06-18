package it.polimi.ingsw.server;

import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.transmittables.StatusMessages;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
     * A Map containing all connections and the relative nicknames
     */
    private final Map<Connection, String> registeredNicknames;
    /**
     * A LinkedList containing players who requested to be inserted in a lobby, in order of arrival
     */
    private final LinkedList<Connection> lobbyRequestingConnections;
    /**
     * The lock used to synchronize over currentLobbyPlayerCount
     */
    private final Object playerCountLock;
    /**
     * The connection that arrived first and who has control of the player count for the lobby
     */
    private Connection firstConnection;
    /**
     * The maximum number of players for the current lobby
     */
    private int currentLobbyPlayerCount;
    /**
     * The current status of the LobbyBuilder Thread
     */
    private volatile boolean active;

    /**
     * The class constructor
     *
     * @param server the Server reference
     */
    ServerLobbyBuilder(Server server) {
        this.server = server;
        this.registeredNicknames = new ConcurrentHashMap<>();
        this.lobbyRequestingConnections = new LinkedList<>();
        this.active = true;
        this.playerCountLock = new Object();
    }

    /**
     * This method accepts a nickname and a connection and, if both are valid, registers the nickname, along with the
     * connection
     *
     * @param nickname   a String representing the nickname of the user
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
        registeredNicknames.entrySet().stream()
                .filter(entry -> entry.getValue().equals(nickname))
                .forEach(entry -> registeredNicknames.remove(entry.getKey(), entry.getValue()));
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

        final int minPlayersPerGame = 2;
        final int maxPlayersPerGame = 3;

        if (playerCount > maxPlayersPerGame || playerCount < minPlayersPerGame) {
            return false;
        }

        synchronized (playerCountLock) {
            if (currentLobbyPlayerCount != 0) {
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
     *
     * @return the maximum number of players allowed in the lobby
     */
    public int getCurrentLobbyPlayerCount() {
        return currentLobbyPlayerCount;
    }

    /**
     * This method handles a request to join the lobby by a player. If nickname and connection are both valid,
     * the method adds the user to the queue, notifies all waiting threads and sends an OK on the connection
     *
     * @param nickname   a String representing the nickname of the user is communicating
     * @param connection the Connection from which the user is
     */
    public boolean handleLobbyRequest(String nickname, Connection connection) {
        synchronized (registeredNicknames) {
            if (!registeredNicknames.containsValue(nickname) || !registeredNicknames.containsKey(connection)) {
                return false;
            }
        }
        synchronized (lobbyRequestingConnections) {
            lobbyRequestingConnections.add(connection);
            lobbyRequestingConnections.notifyAll();
        }
        return true;
    }

    /**
     * This method handles a disconnection in the setup phase.
     *
     * @param connection the disconnecting connection
     * @return true if there were no errors
     */
    public boolean handleDisconnection(Connection connection) {
        synchronized (lobbyRequestingConnections) {
            //erase itself from records in the server, apart from first connection
            lobbyRequestingConnections.removeIf(requestingConnection -> requestingConnection.equals(connection));
            registeredNicknames.remove(connection);
            server.removeHandler(connection);
            synchronized (playerCountLock) {
                //check if first connection and if a current lobby player count has not been selected yet
                //this part is necessary to avoid problems from line 204 on
                //in this case we signal this problem by changing player count lock to -1
                if (firstConnection != null && firstConnection.equals(connection) && currentLobbyPlayerCount == 0) {
                    currentLobbyPlayerCount = -1;
                    playerCountLock.notifyAll();
                }
            }
            connection.close();
        }
        return true;
    }

    /**
     * This method begins the main ServerLobbyBuilder thread, waiting for lobby requests and handling them
     * in order to generate a Match.
     */
    void start() {
        while (active) {
            waitForFirstConnection();

            waitForCurrentLobbyPlayerCount();

            List<AbstractMap.SimpleEntry<Connection, String>> participants = getParticipantsList();

            //if the first player didn't disconnect, then go ahead and create a match
            if (!participants.isEmpty()) {
                Match match = new Match(server);
                participants.forEach(participant -> match.addParticipant(participant.getValue(), participant.getKey()));
                server.submitMatch(match);
            }
        }
    }

    private List<AbstractMap.SimpleEntry<Connection, String>> getParticipantsList() {
        List<AbstractMap.SimpleEntry<Connection, String>> participants = new LinkedList<>();

        synchronized (lobbyRequestingConnections) {
            waitForParticipants();

            //check if the first player disconnected in the meantime
            boolean firstPlayerDisconnected =
                    currentLobbyPlayerCount == -1 || !firstConnection.equals(lobbyRequestingConnections.get(0));

            if (!firstPlayerDisconnected) {
                //At this point we copy the necessary connections and nicknames to guarantee coherence after on
                participants = lobbyRequestingConnections.subList(0, currentLobbyPlayerCount).stream()
                        .map(connection ->
                                new AbstractMap.SimpleEntry<>(connection, registeredNicknames.get(connection))
                        )
                        .collect(Collectors.toList());
                for (int i = 0; i < currentLobbyPlayerCount; i++) {
                    lobbyRequestingConnections.removeFirst();
                }
            }
        }
        return participants;
    }

    private void waitForParticipants() {
        synchronized (lobbyRequestingConnections) {
            while (lobbyRequestingConnections.size() < currentLobbyPlayerCount && lobbyRequestingConnections.get(0).equals(firstConnection)) {
                try {
                    lobbyRequestingConnections.wait();
                } catch (InterruptedException e) {
                    LOGGER.log(Level.FINE, "Interrupting thread while waiting for participants following InterruptedException", e);
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private void waitForCurrentLobbyPlayerCount() {
        synchronized (playerCountLock) {
            currentLobbyPlayerCount = 0;
            firstConnection.send(StatusMessages.CONTINUE);
            while (currentLobbyPlayerCount == 0) {
                try {
                    playerCountLock.wait();
                } catch (InterruptedException e) {
                    LOGGER.log(Level.FINE, "Interrupting thread while waiting for players count following InterruptedException", e);
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private void waitForFirstConnection() {
        synchronized (lobbyRequestingConnections) {
            while (lobbyRequestingConnections.isEmpty()) {
                try {
                    lobbyRequestingConnections.wait();
                } catch (InterruptedException e) {
                    LOGGER.log(Level.FINE, "Interrupting thread while waiting for first connection following InterruptedException", e);
                    Thread.currentThread().interrupt();
                }
            }
            firstConnection = lobbyRequestingConnections.getFirst();
        }
    }

    /**
     * This method stops the main ServerLobbyBuilder thread
     */
    public void stop() {
        active = false;
    }
}
