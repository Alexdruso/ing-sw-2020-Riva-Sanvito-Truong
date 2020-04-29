package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractShowGamePassiveClientState;

/**
 * The CLI-specific  ClientState.
 */
public class InGameCLIClientState extends AbstractShowGamePassiveClientState implements CLIClientState {
    private final CLI cli;
    private CLIClientTurnState clientTurnState;

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
//        cli.drawBoard(temp);
//        cli.printPlayers(tempgame);

        clientTurnState.render(this, client);
    }

}



/*

client
    clientstates
        turnstates
    reducedmodel
    ui
        cli
            turnstates
        gui
            turnstates



 */