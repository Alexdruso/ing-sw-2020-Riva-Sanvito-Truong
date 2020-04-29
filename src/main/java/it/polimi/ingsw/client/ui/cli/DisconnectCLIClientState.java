package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractDisconnectClientState;

/**
 * The CLI-specific DISCONNECT ClientState.
 */
public class DisconnectCLIClientState extends AbstractDisconnectClientState implements CLIClientState {
    private final CLI cli;

    /**
     * Instantiates a new CLI-specific DISCONNECT ClientState.
     *
     * @param client the client
     */
    public DisconnectCLIClientState(Client client) {
        super(client);
        cli = (CLI) client.getUI();
    }

    @Override
    public void render() {
        cli.println("Disconnesso dal server. A presto!");
    }

    @Override
    public void notifyUiInteraction() {
        // No user interaction expected while disconnecting
    }

}
