package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractBuildClientTurnState;
import it.polimi.ingsw.client.reducedmodel.ReducedBoard;
import it.polimi.ingsw.client.reducedmodel.ReducedCell;
import it.polimi.ingsw.client.reducedmodel.ReducedGame;
import it.polimi.ingsw.client.reducedmodel.ReducedTurn;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import it.polimi.ingsw.utils.messages.ReducedComponent;

public class BuildCLIClientTurnState extends AbstractBuildClientTurnState implements CLIClientTurnState {
    private final InGameCLIClientState clientState;
    private final CLI cli;

    public BuildCLIClientTurnState(Client client, InGameCLIClientState clientState) {
        super(client);
        this.clientState = clientState;
        this.cli = (CLI) client.getUI();
    }

    @Override
    public void render() {
        if (client.isCurrentlyActive()) {
            ReducedGame game = client.getGame();
            ReducedTurn turn = game.getTurn();
            ReducedBoard board = game.getBoard();
            boolean workerWasForced = false;
            if (turn.getAllowedWorkers().size() == 1) {
                workerID = turn.getAllowedWorkers().get(0);
                workerWasForced = true;
            }
            while (workerID == null) {
                ReducedCell sourceCell;
                if (turn.isSkippable()) {
                    sourceCell = cli.readCell(board, String.format("%s (%s)", I18n.string(I18nKey.WHICH_WORKER_DO_YOU_WANT_TO_USE_TO_BUILD), I18n.string(I18nKey.X_TO_SKIP)), true);
                    if (sourceCell == null) {
                        notifyUiInteraction();
                        return;
                    }
                }
                else {
                    sourceCell = cli.readCell(board, I18n.string(I18nKey.WHICH_WORKER_DO_YOU_WANT_TO_USE_TO_BUILD));
                }
                sourceCell.getWorker().ifPresent(worker -> {
                    if (worker.getPlayer().getUser().equals(client.getCurrentActiveUser())) {
                        workerID = worker.getWorkerID();
                    }
                });
                if (workerID == null) {
                    cli.error(I18n.string(I18nKey.CHOOSE_ONE_OF_YOUR_WORKERS));
                }
                else {
                    if (!turn.getAllowedWorkers().contains(workerID)) {
                        cli.error(I18n.string(I18nKey.YOU_CANT_BUILD_WITH_THE_SPECIFIED_WORKER));
                        workerID = null;
                    }
                }
            }

            board.getTargets(turn.getWorkerBlockBuildableCells(workerID)).forEach(
                    targetedCell -> targetedCell.setHighlighted(true)
            );
            board.getTargets(turn.getWorkerDomeBuildableCells(workerID)).forEach(
                    targetedCell -> targetedCell.setHighlighted(true)
            );
            clientState.redrawInGameElements();

            ReducedCell targetCell;
            if (!turn.isSkippable() && workerWasForced) {
                targetCell = cli.readCell(board, I18n.string(I18nKey.WHERE_DO_YOU_WANT_TO_BUILD));
            }
            else {
                targetCell = cli.readCell(board, String.format("%s (%s)", I18n.string(I18nKey.WHERE_DO_YOU_WANT_TO_BUILD), I18n.string(workerWasForced ? I18nKey.X_TO_SKIP : I18nKey.X_TO_CANCEL)), true);
            }
            board.getTargets(turn.getWorkerBlockBuildableCells(workerID)).forEach(
                targetedCell -> targetedCell.setHighlighted(false)
            );
            board.getTargets(turn.getWorkerDomeBuildableCells(workerID)).forEach(
                    targetedCell -> targetedCell.setHighlighted(false)
            );
            if (targetCell == null) {
                workerID = null;
                if (workerWasForced) {
                    notifyUiInteraction();
                }
                else {
                    client.requestRender();
                }
                return;
            }
            targetCellX = targetCell.getX();
            targetCellY = targetCell.getY();
            builtLevel = targetCell.getTowerHeight();

            boolean canBuildBlock = turn.getWorkerBlockBuildableCells(workerID).getPosition(targetCellX, targetCellY);
            boolean canBuildDome = turn.getWorkerDomeBuildableCells(workerID).getPosition(targetCellX, targetCellY);
            if (canBuildBlock && canBuildDome) {
                component = null;
                while (component == null) {
                    String choice = cli.readString(I18n.string(I18nKey.DO_YOU_WANT_TO_BUILD_A_BLOCK_OR_A_DOME));
                    if (choice.equalsIgnoreCase(I18n.string(I18nKey.BLOCK))) {
                        component = ReducedComponent.BLOCK;
                    }
                    else if (choice.equalsIgnoreCase(I18n.string(I18nKey.DOME))) {
                        component = ReducedComponent.DOME;
                    }
                    else {
                        cli.error(I18n.string(I18nKey.CHOOSE_BETWEEN_BLOCK_OR_DOME));
                    }
                }
            }
            else {
                component = canBuildBlock ? ReducedComponent.BLOCK : ReducedComponent.DOME;
            }

            clientState.notifyUiInteraction();
        }
        else {
            cli.println(String.format(I18n.string(I18nKey.WAIT_FOR_S_TO_PERFORM_THEIR_BUILD), client.getCurrentActiveUser().nickname));
        }

    }
}
