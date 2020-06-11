package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ui.UI;
import it.polimi.ingsw.client.ui.cli.CLI;
import it.polimi.ingsw.client.ui.gui.GUI;
import it.polimi.ingsw.utils.LoggerManager;

public class ClientApp {
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
