package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ui.UI;
import it.polimi.ingsw.client.ui.cli.CLI;
import it.polimi.ingsw.client.ui.gui.GUI;
import it.polimi.ingsw.utils.LoggerManager;

/**
 * The entrypoint of the Santorini game client.
 *
 * @see it.polimi.ingsw.client
 */
public class ClientApp {
    /**
     * Initializes and launches the client.
     * To run the client with a GUI, do not specify any command line argument.
     * To run the client with a CLI, specify "cli" as the first command line argument.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LoggerManager.setLogLevel();
        UI ui;
        if (args.length > 0 && args[0].equalsIgnoreCase("cli")) {
            ui = new CLI();
        }
        else {
            ui = new GUI();
        }
        Client client = new Client(ui);
        client.run();
    }
}
