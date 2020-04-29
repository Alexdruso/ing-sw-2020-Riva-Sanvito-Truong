package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractConnectToServerClientState;
import it.polimi.ingsw.config.ConfigParser;

/**
 * The CLI-specific CONNECT_TO_SERVER ClientState.
 */
public class ConnectToServerCLIClientState extends AbstractConnectToServerClientState implements CLIClientState {
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
        port = cli.readInt("Porta:", ConfigParser.getInstance().getIntProperty("serverPort"));
        notifyUiInteraction();
    }


}
