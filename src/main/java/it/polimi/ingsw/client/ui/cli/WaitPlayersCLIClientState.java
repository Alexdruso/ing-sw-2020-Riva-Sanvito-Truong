package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractJoinLobbyClientState;
import it.polimi.ingsw.client.clientstates.AbstractWaitPlayersClientState;

/**
 * The CLI-specific JOIN_LOBBY ClientState.
 */
public class WaitPlayersCLIClientState extends AbstractWaitPlayersClientState implements CLIClientState {
    private final CLI cli;

    /**
     * Instantiates a new CLI-specific JOIN_LOBBY ClientState.
     *
     * @param client the client
     */
    public WaitPlayersCLIClientState(Client client) {
        super(client);
        cli = (CLI) client.getUI();
    }

    @Override
    public void render() {
        cli.println("In attesa degli altri giocatori...");
    }


}
