package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractDisconnectClientState;

/**
 * The CLI-specific DISCONNECT ClientState.
 */
public class DisconnectCliClientState extends AbstractDisconnectClientState {
    private final Cli cli;

    /**
     * Instantiates a new CLI-specific DISCONNECT ClientState.
     *
     * @param client the client
     */
    public DisconnectCliClientState(Client client) {
        super(client);
        cli = (Cli) client.getUi();
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
