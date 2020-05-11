package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractMoveClientTurnState;
import it.polimi.ingsw.client.reducedmodel.ReducedCell;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;

public class MoveCLIClientTurnState extends AbstractMoveClientTurnState implements CLIClientTurnState {
    private final InGameCLIClientState clientState;
    private final CLI cli;

    public MoveCLIClientTurnState(Client client, InGameCLIClientState clientState) {
        super(client);
        this.clientState = clientState;
        this.cli = (CLI) client.getUI();
    }

    @Override
    public void render() {
        if (client.isCurrentlyActive()) {
            while (workerID == null) {
                ReducedCell sourceCell = cli.readCell(client.getGame().getBoard(), I18n.string(I18nKey.WHICH_WORKER_DO_YOU_WANT_TO_MOVE));
                sourceCellX = sourceCell.getX();
                sourceCellY = sourceCell.getY();
                sourceCell.getWorker().ifPresent(worker -> {
                    if (worker.getPlayer().getUser().equals(client.getCurrentActiveUser())) {
                        workerID = worker.getWorkerID();
                    }
                });
                if (workerID == null) {
                    cli.error(I18n.string(I18nKey.CHOOSE_ONE_OF_YOUR_WORKERS));
                }
            }

            ReducedCell targetCell = cli.readCell(client.getGame().getBoard(), I18n.string(I18nKey.WHERE_DO_YOU_WANT_TO_PLACE_YOUR_WORKER));
            targetCellX = targetCell.getX();
            targetCellY = targetCell.getY();

            clientState.notifyUiInteraction();
        }
        else {
            cli.println(String.format(I18n.string(I18nKey.WAIT_FOR_S_TO_PERFORM_THEIR_MOVE), client.getCurrentActiveUser().nickname));
        }
    }

}
