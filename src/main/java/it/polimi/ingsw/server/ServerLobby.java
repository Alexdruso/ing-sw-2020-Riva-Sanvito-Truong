package it.polimi.ingsw.server;

import it.polimi.ingsw.config.ConfigParser;
import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.networking.Connection;

import java.util.HashMap;
import java.util.Map;

public class ServerLobby {
    private Map<String, Connection> connectedUsers;
    private int lobbyPlayerCount; //Note: not using an Optional here since it is highly discouraged to synchronize on an optional
    private Server server;
    private final int MIN_PLAYERS_PER_GAME;
    private final int MAX_PLAYERS_PER_GAME;

    public ServerLobby(Server server){
        ConfigParser configParser = ConfigParser.getInstance();
        MIN_PLAYERS_PER_GAME = Integer.parseInt(configParser.getProperty("minPlayersPerGame"));
        MAX_PLAYERS_PER_GAME = Integer.parseInt(configParser.getProperty("maxPlayersPerGame"));
        this.connectedUsers = new HashMap<String, Connection>();
        this.server= server;
        this.lobbyPlayerCount = 0;
    }

    public boolean setPlayerCount(int playerCount){
        if(lobbyPlayerCount == 0 && playerCount >= MIN_PLAYERS_PER_GAME && playerCount <= MAX_PLAYERS_PER_GAME){
            lobbyPlayerCount = playerCount;
            notifyAll();
            return true;
        } else {
            return false;
        }
    }

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

    public Map<String, Connection> getConnectedUsers(){
        return new HashMap<String, Connection>(connectedUsers);
    }

}
