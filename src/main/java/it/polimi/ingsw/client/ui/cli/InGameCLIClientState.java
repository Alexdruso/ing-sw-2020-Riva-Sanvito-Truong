package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractInGameClientState;

/**
 * The CLI-specific  ClientState.
 */
public class InGameCLIClientState extends AbstractInGameClientState implements CLIClientState {
    private final CLI cli;

    /**
     * Instantiates a new CLI-specific  ClientState.
     *
     * @param client the client
     */
    public InGameCLIClientState(Client client) {
        super(client);
        cli = (CLI) client.getUI();
    }

    @Override
    public void render() {
        cli.clear();
        cli.drawBoard(client.getGame().getBoard());
        cli.printPlayersOfGame(client.getGame());
        cli.moveCursorToStatusPosition();

        client.getGame().getTurn().getTurnState().render();
    }

}
