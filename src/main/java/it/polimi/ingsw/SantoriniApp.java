package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ui.UI;
import it.polimi.ingsw.client.ui.cli.CLI;
import it.polimi.ingsw.client.ui.gui.GUI;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.utils.LoggerManager;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The entrypoint of the Santorini game.
 *
 * @see it.polimi.ingsw.client
 * @see it.polimi.ingsw.server
 */
public class SantoriniApp {
    private static final Logger LOGGER = Logger.getLogger(SantoriniApp.class.getName());

    /**
     * The Santorini components that can be launched.
     */
    private enum LaunchComponent {
        CLI, GUI, SERVER
    }

    /**
     * Initializes and launches the Santorini app.
     * To run the client with a GUI, do not specify any command line argument or specify "gui".
     * To run the client with a CLI, specify "cli" as the first command line argument.
     * To run the server, specify "server" as the first command line argument.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LoggerManager.setLogLevel();

        String launchComponentValue = args.length > 0 ? args[0] : "gui";
        LaunchComponent launchComponent = LaunchComponent.valueOf(launchComponentValue.toUpperCase());

        switch (launchComponent) {
            case CLI -> launchClient(false);
            case GUI -> launchClient(true);
            case SERVER -> launchServer();
        }
    }

    /**
     * Initializes and launches the Santorini client app.
     *
     * @param withGUI whether to launch the GUI version of the client
     */
    private static void launchClient(boolean withGUI) {
        UI ui;
        if (withGUI) {
            ui = new GUI();
        }
        else {
            ui = new CLI();
        }
        Client client = new Client(ui);
        client.run();
    }

    /**
     * Initializes and launches the Santorini server app
     */
    private static void launchServer() {
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
