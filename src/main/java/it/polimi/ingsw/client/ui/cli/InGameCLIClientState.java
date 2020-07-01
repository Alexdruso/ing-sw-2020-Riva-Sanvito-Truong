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
        redrawInGameElements();
        client.getGame().getTurn().getTurnState().render();
    }

    /**
     * Redraws the IN_GAME CLI screen, with the players, board, legend; then, moves the cursor to the status position.
     */
    void redrawInGameElements() {
        cli.clear();
        cli.printPlayersOfGame(client.getGame());
        cli.drawLegend();
        cli.drawBoard(client.getGame().getBoard());
        cli.moveCursorToStatusPosition();
    }

}
