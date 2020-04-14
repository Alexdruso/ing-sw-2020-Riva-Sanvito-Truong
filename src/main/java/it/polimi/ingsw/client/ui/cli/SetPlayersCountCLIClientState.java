package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractSetPlayersCountClientState;

/**
 * The CLI-specific SET_PLAYERS_COUNT ClientState.
 */
public class SetPlayersCountCLIClientState extends AbstractSetPlayersCountClientState {
    private final CLI cli;

    /**
     * Instantiates a new CLI-specific SET_PLAYERS_COUNT ClientState.
     *
     * @param client the client
     */
    public SetPlayersCountCLIClientState(Client client) {
        super(client);
        cli = (CLI) client.getUI();
    }

    @Override
    public void render() {
        cli.println("Sei il primo giocatore sul server!");
        players_count = cli.readInt("Numero di giocatori [2-3]:");
        notifyUiInteraction();
    }
}
