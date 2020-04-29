package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractJoinLobbyClientState;

/**
 * The CLI-specific JOIN_LOBBY ClientState.
 */
public class JoinLobbyCLIClientState extends AbstractJoinLobbyClientState implements CLIClientState {
    private final CLI cli;

    /**
     * Instantiates a new CLI-specific JOIN_LOBBY ClientState.
     *
     * @param client the client
     */
    public JoinLobbyCLIClientState(Client client) {
        super(client);
        cli = (CLI) client.getUI();
    }

    @Override
    public void render() {
        cli.println("Ricerca di una partita...");
    }

}
