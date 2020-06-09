package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractShowGamePassiveClientState;
import it.polimi.ingsw.client.reducedmodel.*;
import it.polimi.ingsw.utils.networking.transmittables.ReducedGod;
import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;
import it.polimi.ingsw.utils.networking.transmittables.ReducedWorkerID;

import java.util.Arrays;
import java.util.Random;

/**
 * The CLI-specific SHOW_GAME_PASSIVE ClientState.
 */
public class ShowGamePassiveCLIClientState extends AbstractShowGamePassiveClientState implements CLIClientState {
    private final CLI cli;
    private final Random r = new Random();

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

        ReducedPlayer p1 = new ReducedPlayer(new ReducedUser("pippo"), false, 0);
        ReducedPlayer p2 = new ReducedPlayer(new ReducedUser("LOL"), true, 1);
        ReducedPlayer p3 = new ReducedPlayer(new ReducedUser("zaza"), false, 2);
        p1.setGod(new ReducedGod("Apollo"));
        p2.setGod(new ReducedGod("Artemis"));
        p3.setGod(new ReducedGod("Athena"));
        ReducedGame tempgame = new ReducedGame(Arrays.asList(p1, p2, p3));
        tempgame.setTurn(new ReducedTurn(p1, null));
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

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                temp.getCell(i, j).setTowerHeight(r.nextInt(4));
                temp.getCell(i, j).setHasDome(r.nextInt(100) < 8);
            }
        }

        while (Thread.currentThread().isAlive()) {
            cli.clear();
            cli.drawBoard(temp);
            cli.printPlayersOfGame(tempgame);
            cli.drawLegend();
            cli.moveCursorToStatusPosition();
            ReducedCell ctemp = cli.readCell(temp, "Quale worker vuoi usare per la tua mossa? (lettera o coordinate)");
            ctemp.setHighlighted(true);
        }
    }

}
