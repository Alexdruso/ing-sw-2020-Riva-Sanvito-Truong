package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractJoinLobbyClientState;
import it.polimi.ingsw.client.clientstates.AbstractShowGamePassiveClientState;
import it.polimi.ingsw.client.reducedmodel.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The CLI-specific SHOW_GAME_PASSIVE ClientState.
 */
public class ShowGamePassiveCLIClientState extends AbstractShowGamePassiveClientState implements CLIClientState {
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
        cli.clear();

        ReducedPlayer p1 = new ReducedPlayer("pippo", false, 0);
        ReducedPlayer p2 = new ReducedPlayer("LOC", true, 1);
        ReducedPlayer p3 = new ReducedPlayer("zaza", false, 2);
        ReducedGame tempgame = new ReducedGame(Arrays.asList(p1, p2, p3));
        tempgame.setTurn(new ReducedTurn(p1));
        ReducedBoard temp = new ReducedBoard();
        ReducedWorker p1w1 = new ReducedWorker(ReducedWorkerID.WORKER1, p1);
        ReducedWorker p1w2 = new ReducedWorker(ReducedWorkerID.WORKER2, p1);
        ReducedWorker p2w1 = new ReducedWorker(ReducedWorkerID.WORKER1, p2);
        ReducedWorker p2w2 = new ReducedWorker(ReducedWorkerID.WORKER2, p2);
        ReducedWorker p3w1 = new ReducedWorker(ReducedWorkerID.WORKER1, p3);
        ReducedWorker p3w2 = new ReducedWorker(ReducedWorkerID.WORKER2, p3);
        temp.getCell(1,1).setWorker(p1w1);
        temp.getCell(4,0).setWorker(p1w2);
        temp.getCell(2,3).setWorker(p2w1);
        temp.getCell(0,3).setWorker(p2w2);
        temp.getCell(3,2).setWorker(p3w1);
        temp.getCell(4,1).setWorker(p3w2);

        cli.drawBoard(temp);
        cli.printPlayers(tempgame);
        cli.println("", 28, 0);
        cli.readString("Quale worker vuoi usare per la tua mossa? (lettera o coordinate)", null, 3);
        render();
    }

}
