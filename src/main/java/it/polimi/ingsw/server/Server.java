package it.polimi.ingsw.server;

import it.polimi.ingsw.config.ConfigParser;
import it.polimi.ingsw.utils.networking.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private ServerSocket serverSocket;
    private ExecutorService executor;
    private ArrayList<ServerLobby> lobbies;

    public Server() throws IOException {
        ConfigParser configParser = ConfigParser.getInstance();
        int SERVER_PORT = Integer.parseInt(configParser.getProperty("serverPort"));
        int n_THREADS = Integer.parseInt(configParser.getProperty("numberOfThreads"));
        serverSocket = new ServerSocket(SERVER_PORT);
        executor = Executors.newFixedThreadPool(n_THREADS);
        lobbies = new ArrayList<ServerLobby>();
        lobbies.add(new ServerLobby(this)); //For now we create only one lobby, however it is easily expandable
    }

    boolean setPlayerCount(int playerCount){
        return lobbies.get(0).setPlayerCount(playerCount);
    }

    void joinLobby(String nickname, Connection connection) {
        //WIP: currently joins only single lobby
        lobbies.get(0).joinLobby(nickname, connection);
    }

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
        lobbies.remove(0);
        lobbies.add(0, new ServerLobby(this));
    }

    public void shutdown(){
        try {
            serverSocket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void start(){
        while(true){
            try{
                Socket inboundSocket = serverSocket.accept();
                Connection currentConnection = new Connection(inboundSocket);
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
