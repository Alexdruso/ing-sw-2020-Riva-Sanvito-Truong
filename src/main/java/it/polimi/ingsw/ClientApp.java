package it.polimi.ingsw;


import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ui.cli.Cli;

public class ClientApp {
    public static void main(String[] args) {
        Client client = new Client(new Cli());
        client.run();
    }
}
