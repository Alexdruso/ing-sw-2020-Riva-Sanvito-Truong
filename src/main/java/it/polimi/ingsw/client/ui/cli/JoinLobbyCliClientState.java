package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractJoinLobbyClientState;
import it.polimi.ingsw.client.clientstates.AbstractSetNicknameClientState;

/**
 * The CLI-specific JOIN_LOBBY ClientState.
 */
public class JoinLobbyCliClientState extends AbstractJoinLobbyClientState {
    private final Cli cli;

    /**
     * Instantiates a new CLI-specific JOIN_LOBBY ClientState.
     *
     * @param client the client
     */
    public JoinLobbyCliClientState(Client client) {
        super(client);
        cli = (Cli) client.getUi();
    }

    @Override
    public void render() {
        cli.println("Ricerca di una partita...");
    }

    @Override
    public void notifyUiInteraction() {
        // No user interaction expected while waiting for match
    }

}
