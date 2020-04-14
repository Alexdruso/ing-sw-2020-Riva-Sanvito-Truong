package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractConnectToServerClientState;
import it.polimi.ingsw.client.ui.Ui;

/**
 * The CLI-specific CONNECT_TO_SERVER ClientState.
 */
public class ConnectToServerCliClientState extends AbstractConnectToServerClientState {
    private final Cli cli;

    /**
     * Instantiates a new CLI-specific CONNECT_TO_SERVER ClientState.
     *
     * @param client the client
     */
    public ConnectToServerCliClientState(Client client) {
        super(client);
        cli = (Cli) client.getUi();
    }

    @Override
    public void render() {
        host = cli.readString("Indirizzo del server:");
        port = cli.readInt("Porta:");
        notifyUiInteraction();
    }


}
