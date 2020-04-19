package it.polimi.ingsw.server;

import it.polimi.ingsw.config.ConfigParser;
import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.networking.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class represents the Server. Whenever one Server object is created and run, it waits for
 * connections arriving over the network and dispatches the setup to a ServerConnectionSetupHandler object
 */
public class Server {
    /**
     * The socket from which the Server listens for new requests
     */
    private ServerSocket serverSocket;
    /**
     * The executor pools for the Matches
     */
    private ExecutorService executor;
    /**
     * The ServerLobby that is currently being filled
     */
    private ServerLobby lobby;
    /**
     *
     */
    private final ArrayList<Match> ongoingMatches;
    /**
     *
     */
    private final Map<Connection, String> registeredNicknames;
    /**
     *
     */
    private final LinkedList<Connection> lobbyRequestingConnections;
    private Connection firstConnection;

    private int currentLobbyPlayerCount;

    private final Object playerCountLock;

    private boolean active;
    /**
     * Class constructor
     */
    public Server() throws IOException {
        ConfigParser configParser = ConfigParser.getInstance();
        int SERVER_PORT = Integer.parseInt(configParser.getProperty("serverPort"));
        int n_THREADS = Integer.parseInt(configParser.getProperty("numberOfThreads"));
        serverSocket = getServerSocket(SERVER_PORT);
        ongoingMatches = new ArrayList<>();
        registeredNicknames = new ConcurrentHashMap<>();
        executor = Executors.newFixedThreadPool(n_THREADS);
        lobbyRequestingConnections = new LinkedList<>();
        playerCountLock = new Object();
        active = true;
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

    ServerSocket getServerSocket(int port) throws IOException{
        return new ServerSocket(port);
    }

    Connection getConnection(Socket inboundSocket) throws IOException{
        return new Connection(inboundSocket);
    }

    List<Match> getOngoingMatches(){
        return new ArrayList<>(ongoingMatches);
    }

    int getCurrentLobbyPlayerCount(){
        return currentLobbyPlayerCount;
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

    public void handleLobbyRequest(String nickname, Connection connection){
        synchronized(lobbyRequestingConnections){
            lobbyRequestingConnections.add(connection);
            lobbyRequestingConnections.notify();
            connection.send(StatusMessages.OK);
        }
    }

    /**
     * When a ServerLobby is complete, it calls this method to create a Match object and execute it
     * as a different thread
     * @param lobby
     */
    void createMatch(ServerLobby lobby){
        LinkedHashMap<String, Connection> connectedUsers = lobby.getConnectedUsers();
        Match match = new Match();

        match.addParticipants(connectedUsers);
        executor.submit(match);
        ongoingMatches.add(match);
    }

    /**
     * This method shuts down the server by closing the ServerSocket
     */
    public void shutdown(){
        try {
            serverSocket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        active = false;
    }

    public void startLobbyThread(){
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
            lobby = new ServerLobby(this, currentLobbyPlayerCount);
            for(int i = 0; i < currentLobbyPlayerCount; i++){
                Connection connection = lobbyRequestingConnections.removeFirst();
                String nickname = registeredNicknames.get(connection);
                lobby.joinLobby(nickname, connection);
            }
            createMatch(lobby);
            lobby = null;
        }
    }

    /**
     * This is the main method of the server, which runs infinitely until the server is shut down.
     * This method accepts inbound connections and dispatches them
     */
    public void startAcceptingThread(){
        while(!serverSocket.isClosed()){
            try{
                Socket inboundSocket = serverSocket.accept();
                Connection currentConnection = getConnection(inboundSocket);
                ServerConnectionSetupHandler connectionHandler = new ServerConnectionSetupHandler(this, currentConnection);
                currentConnection.addObserver(connectionHandler);
            } catch (IOException e){
                //TODO: Send to logger instead of stdout
                e.printStackTrace();
                System.out.println("Socket closed");
            }
        }
    }

}
