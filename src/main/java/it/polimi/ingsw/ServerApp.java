package it.polimi.ingsw;

import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.utils.LoggerManager;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The entrypoint of the Santorini game server.
 *
 * @see it.polimi.ingsw.server
 */
public class ServerApp {
    private static final Logger LOGGER = Logger.getLogger(ServerApp.class.getName());

    /**
     * Initializes and launches the client.
     *
     * @param args the command line arguments, which are currently ignored by the server.
     */
    public static void main(String[] args) {
        LoggerManager.setLogLevel();
        Server server;
        try{
            server = new Server();
            server.start();
        } catch (IOException e){
            LOGGER.log(Level.SEVERE, "Could not initialize server", e);
            System.exit(1);
        }
    }
}
