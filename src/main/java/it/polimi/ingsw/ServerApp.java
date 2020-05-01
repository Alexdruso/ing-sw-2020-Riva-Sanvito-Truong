package it.polimi.ingsw;

import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.utils.LoggerManager;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerApp {
    private static final Logger LOGGER = Logger.getLogger(ServerApp.class.getName());

    public static void main(String[] args) {
        LoggerManager.setLogLevel();
        Server server;
        try{
            server = new Server();
            server.start();
        } catch (IOException e){
            LOGGER.log(Level.SEVERE, "Could not initialize server", e);
        }
    }
}
