package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ui.cli.CLI;
import it.polimi.ingsw.utils.LoggerManager;

public class ClientApp {
    public static void main(String[] args) {
        LoggerManager.setLogLevel();
        Client client = new Client(new CLI());
        client.run();
    }
}
