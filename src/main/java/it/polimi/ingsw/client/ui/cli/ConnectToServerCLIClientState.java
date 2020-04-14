package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractConnectToServerClientState;

/**
 * The CLI-specific CONNECT_TO_SERVER ClientState.
 */
public class ConnectToServerCLIClientState extends AbstractConnectToServerClientState {
    private final CLI cli;

    /**
     * Instantiates a new CLI-specific CONNECT_TO_SERVER ClientState.
     *
     * @param client the client
     */
    public ConnectToServerCLIClientState(Client client) {
        super(client);
        cli = (CLI) client.getUI();
    }

    @Override
    public void render() {
        host = cli.readString("Indirizzo del server:");
        port = cli.readInt("Porta:");
        notifyUiInteraction();
    }


}