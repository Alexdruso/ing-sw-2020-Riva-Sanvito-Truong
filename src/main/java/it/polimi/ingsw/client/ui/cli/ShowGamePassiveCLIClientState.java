package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractJoinLobbyClientState;
import it.polimi.ingsw.client.clientstates.AbstractShowGamePassiveClientState;

/**
 * The CLI-specific SHOW_GAME_PASSIVE ClientState.
 */
public class ShowGamePassiveCLIClientState extends AbstractShowGamePassiveClientState {
    private final CLI cli;

    /**
     * Instantiates a new CLI-specific SHOW_GAME_PASSIVE ClientState.
     *
     * @param client the client
     */
    public ShowGamePassiveCLIClientState(Client client) {
        super(client);
        cli = (CLI) client.getUI();
    }

    @Override
    public void render() {
        cli.println("TODO: mostrare la board senza possibilita' di controllo");
    }

}
