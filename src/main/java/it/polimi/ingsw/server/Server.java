package it.polimi.ingsw.server;

import it.polimi.ingsw.utils.config.ConfigParser;
import it.polimi.ingsw.utils.networking.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents the Server. Whenever one Server object is created and run, it waits for
 * connections arriving over the network and dispatches the setup to a ServerConnectionSetupHandler object
 */
public class Server {
    /**
     * The constant LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    /**
     * The socket from which the Server listens for new requests
     */
    private final ServerSocket serverSocket;
    /**
     * The executor pools for the Matches
     */
    private final ExecutorService executor;
    /**
     * The ServerLobby that is currently being filled
     */
    private final ServerLobbyBuilder lobbyBuilder;
    /**
     * A List of created and ongoing matches
     */
    private final ArrayList<Match> ongoingMatches;
    /**
     * A Map with Connections as keys and the relative SetupHandler as values
     */
    private final Map<Connection, ServerConnectionSetupHandler> handlers;

    /**
     * Instantiates a new Server.
     *
     * @throws IOException io exception thrown by the socket
     */
    public Server() throws IOException {
        this(ConfigParser.getInstance().getIntProperty("serverPort"));
    }

    /**
     * Instantiates a new Server.
     *
     * @param serverPort the server port
     * @throws IOException io exception thrown by the socket
     */
    public Server(int serverPort) throws IOException {
        ConfigParser configParser = ConfigParser.getInstance();
        int nThreads = configParser.getIntProperty("numberOfThreads");
        LOGGER.log(Level.INFO, () -> String.format("Starting %s server v. %s...", configParser.getProperty("projectName"), configParser.getProperty("version")));
        serverSocket = getServerSocket(serverPort);
        executor = Executors.newFixedThreadPool(nThreads);
        ongoingMatches = new ArrayList<>();
        handlers = new ConcurrentHashMap<>();
        lobbyBuilder = new ServerLobbyBuilder(this);
        new Thread(lobbyBuilder::start, "LobbyBuilder").start();
    }

    /**
     * This method retrieves a new ServerSocket on a given port
     *
     * @param port the port on which the socket is to be opened
     * @return the ServerSocket instance
     * @throws IOException input output exception
     */
    ServerSocket getServerSocket(int port) throws IOException{
        return new ServerSocket(port);
    }

    /**
     * This method retrieves a new Connection object, when given an inbound socket created by the
     * ServerSocket when it receives a connection over the network.
     *
     * @param inboundSocket the inbound socket
     * @return a new connection from the inbound socket
     * @throws IOException an input output exception
     */
    Connection getConnection(Socket inboundSocket) throws IOException{
        return new Connection(inboundSocket);
    }

    /**
     * This method returns a list containing all matches that have been created
     *
     * @return the list with the reference to the created matches
     */
    List<Match> getOngoingMatches(){
        synchronized (ongoingMatches) {
            return new ArrayList<>(ongoingMatches);
        }
    }

    /**
     * This method returns the ServerLobbyBuilder connected to the server
     *
     * @return the ServerLobbyBuilder instance
     */
    ServerLobbyBuilder getLobbyBuilder(){
        return lobbyBuilder;
    }

    /**
     * This method accepts a Match instance and executes it in a different thread
     *
     * @param match the Match to be executed
     */
    void submitMatch(Match match) {
        executor.submit(match);
        synchronized (ongoingMatches) {
            ongoingMatches.add(match);
        }
            for (Connection connection : match.getParticipantsNicknameToConnection().values()) {
                synchronized (handlers) {
                    connection.removeObserver(handlers.get(connection));
                    handlers.remove(connection);
                }
            }
        }

    /**
     * This method removes a Match instance from the ongoing matches.
     *
     * @param match the match we need to remove
     */
    void removeMatch(Match match) {
        synchronized (ongoingMatches) {
            ongoingMatches.removeIf(match::equals);
        }
        match.getParticipantsNicknameToConnection()
                .keySet()
                .forEach(lobbyBuilder::removeNickname);
    }

    /**
     * This method removes a handler from the handlers.
     *
     * @param connection the connection related to the handler
     */
    void removeHandler(Connection connection) {
        synchronized (handlers) {
            handlers.remove(connection);
        }
    }

    /**
     * This method shuts down the server by closing the ServerSocket
     */
    public void shutdown() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
    }

    /**
     * This is the main method of the server, which runs infinitely until the server is shut down.
     * This method accepts inbound connections and dispatches them
     */
    public void start(){
        LOGGER.log(Level.INFO, "Server ready to accept connections");
        while(!serverSocket.isClosed()){
            try {
                Socket inboundSocket = serverSocket.accept();
                Connection currentConnection = getConnection(inboundSocket);
                ServerConnectionSetupHandler connectionHandler = new ServerConnectionSetupHandler(this, currentConnection);
                synchronized (handlers) {
                    handlers.put(currentConnection, connectionHandler);
                }
                currentConnection.addObserver(connectionHandler, (obs, message) ->
                        ((ServerConnectionSetupHandler) obs).update(message));
            } catch (IOException e){
                LOGGER.log(Level.WARNING, "Socket closed", e);
            }
        }
    }

}
