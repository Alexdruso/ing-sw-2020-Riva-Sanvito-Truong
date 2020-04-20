package it.polimi.ingsw.server;

import it.polimi.ingsw.config.ConfigParser;
import it.polimi.ingsw.utils.networking.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
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
     * Class constructor. This method also creates a lobby builder and starts its thread
     */
    public Server() throws IOException {
        ConfigParser configParser = ConfigParser.getInstance();
        int SERVER_PORT = Integer.parseInt(configParser.getProperty("serverPort"));
        int n_THREADS = Integer.parseInt(configParser.getProperty("numberOfThreads"));
        serverSocket = getServerSocket(SERVER_PORT);
        executor = Executors.newFixedThreadPool(n_THREADS);
        ongoingMatches = new ArrayList<>();
        lobbyBuilder = new ServerLobbyBuilder(this);
        new Thread(() -> lobbyBuilder.start()).start();
    }

    /**
     * This method retrieves a new ServerSocket on a given port
     * @param port the port on which the socket is to be opened
     * @return the ServerSocket instance
     * @throws IOException
     */
    ServerSocket getServerSocket(int port) throws IOException{
        return new ServerSocket(port);
    }

    /**
     * This method retrieves a new Connection object, when given an inbound socket created by the
     * ServerSocket when it receives a connection over the newtork.
     * @param inboundSocket
     * @return
     * @throws IOException
     */
    Connection getConnection(Socket inboundSocket) throws IOException{
        return new Connection(inboundSocket);
    }

    /**
     * This method returns a list containing all matches that have been created
     * @return the list with the reference to the created matches
     */
    List<Match> getOngoingMatches(){
        return new ArrayList<>(ongoingMatches);
    }

    /**
     * This method returns the ServerLobbyBuilder connected to the server
     * @return the ServerLobbyBuilder instance
     */
    ServerLobbyBuilder getLobbyBuilder(){
        return lobbyBuilder;
    }

    /**
     * This method accepts a Match instance and executes it in a different thread
     * @param match the Match to be executed
     */
    void submitMatch(Match match){
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
