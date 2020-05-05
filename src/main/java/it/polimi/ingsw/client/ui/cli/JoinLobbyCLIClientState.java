package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractJoinLobbyClientState;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;

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
        cli.println("");
        cli.println(I18n.string(I18nKey.LOOKING_FOR_A_MATCH));
    }

}
