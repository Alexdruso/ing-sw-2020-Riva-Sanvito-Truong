package it.polimi.ingsw.server;

import it.polimi.ingsw.config.ConfigParser;
import it.polimi.ingsw.utils.networking.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
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
     * The List of lobbies that are incomplete
     */
    private final ServerLobby[] lobbies;

    /**
     * Class constructor
     */
    public Server() throws IOException {
        ConfigParser configParser = ConfigParser.getInstance();
        int SERVER_PORT = Integer.parseInt(configParser.getProperty("serverPort"));
        int n_THREADS = Integer.parseInt(configParser.getProperty("numberOfThreads"));
        int MAX_LOBBIES = Integer.parseInt(configParser.getProperty("maxNumberOfLobbies"));
        serverSocket = getServerSocket(SERVER_PORT);
        executor = Executors.newFixedThreadPool(n_THREADS);
        lobbies = new ServerLobby[MAX_LOBBIES];
    }

    ServerSocket getServerSocket(int port) throws IOException{
        return new ServerSocket(port);
    }

    Connection getConnection(Socket inboundSocket) throws IOException{
        return new Connection(inboundSocket);
    }

    /**
     * This method is called whenever a Connection receives a setPlayersCount message
     * @param playerCount the number of players
     * @return true if the count has been set correctly, false otherwise (the count has been already set
     * or the number given is invalid
     */
    boolean setPlayerCount(int playerCount){
        return lobbies[0].setPlayerCount(playerCount);
    }

    /**
     * This method is called whenever a Connection receives a request to join a lobby
     * @param nickname
     * @param connection
     */
    void joinLobby(String nickname, Connection connection) {
        lobbies[0].joinLobby(nickname, connection);
    }

    /**
     * When a ServerLobby is complete, it calls this method to create a Match object and execute it
     * as a different thread
     * @param lobby
     */
    void createMatch(ServerLobby lobby){
        Map<String, Connection> connectedUsers = lobby.getConnectedUsers();
        Match match;
        if(connectedUsers.size() == 2){
            String[] usernames = connectedUsers.keySet().toArray(new String[2]);
            Connection[] connections = connectedUsers.values().toArray(new Connection[2]);
            match = new Match(connections[0], usernames[0], connections[1], usernames[1]);
        } else {
            String[] usernames = connectedUsers.keySet().toArray(new String[3]);
            Connection[] connections = connectedUsers.values().toArray(new Connection[3]);
            match = new Match(connections[0], usernames[0], connections[1], usernames[1], connections[2], usernames[2]);
        }
        executor.submit(match);
        lobbies[0] = new ServerLobby(this);
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
    }

    /**
     * This is the main method of the server, which runs infinitely until the server is shut down.
     * This method accepts inbound connections and dispatches them
     */
    public void start(){
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
