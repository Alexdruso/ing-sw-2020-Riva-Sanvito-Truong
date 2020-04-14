package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractConnectToServerClientState;
import it.polimi.ingsw.client.ui.Ui;

public class ConnectToServerCliClientState extends AbstractConnectToServerClientState {
    final Cli cli;

    public ConnectToServerCliClientState(Client client) {
        super(client);
        cli = (Cli) client.getUi();
    }

    @Override
    public void render() {
        cli.println("ciao");
        host = cli.readString("Indirizzo del server:");
        port = cli.readInt("Porta:");
        notifyUiInteraction();
    }
}
