package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractMoveClientTurnState;
import it.polimi.ingsw.client.reducedmodel.*;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;

public class MoveCLIClientTurnState extends AbstractMoveClientTurnState implements CLIClientTurnState {
    private final InGameCLIClientState clientState;
    private final CLI cli;
    private boolean workerWasForced = false;

    public MoveCLIClientTurnState(Client client, InGameCLIClientState clientState) {
        super(client);
        this.clientState = clientState;
        this.cli = (CLI) client.getUI();
    }

    @Override
    public void render() {
        if (client.isCurrentlyActive()) {
            renderCurrentlyActive();
        }
        else {
            renderCurrentlyInactive();
        }
    }

    /**
     * Renders the move CLI client turn state for the currently active player.
     */
    private void renderCurrentlyActive() {
        ReducedGame game = client.getGame();
        ReducedTurn turn = game.getTurn();
        ReducedBoard board = game.getBoard();
        ReducedPlayer player = turn.getPlayer();
        boolean shouldReturn;

        shouldReturn = getSourceCell(turn, board, player);
        if (shouldReturn) return;

        board.getTargets(turn.getWorkerWalkableCells(workerID)).forEach(
                targetedCell -> targetedCell.setHighlighted(true)
        );
        clientState.redrawInGameElements();

        shouldReturn = getTargetCell(turn, board);
        if (shouldReturn) return;

        clientState.notifyUiInteraction();
    }

    /**
     * Obtains from the user the source cell of the movement they want to perform.
     *
     * @param turn   the current turn
     * @param board  the board of the game
     * @param player the current turn's player
     * @return whether this turn should end immediately
     */
    private boolean getSourceCell(ReducedTurn turn, ReducedBoard board, ReducedPlayer player) {
        if (turn.getAllowedWorkers().size() == 1) {
            workerID = turn.getAllowedWorkers().get(0);
            workerWasForced = true;
            ReducedWorker worker = player.getWorker(workerID);
            ReducedCell workerCell = worker.getCell();
            sourceCellX = workerCell.getX();
            sourceCellY = workerCell.getY();
        }
        while (workerID == null) {
            ReducedCell sourceCell;
            if (turn.isSkippable()) {
                sourceCell = cli.readCell(board, String.format("%s (%s)", I18n.string(I18nKey.WHICH_WORKER_DO_YOU_WANT_TO_MOVE), I18n.string(I18nKey.X_TO_SKIP)), true);
                if (sourceCell == null) {
                    clientState.notifyUiInteraction();
                    return true;
                }
            }
            else {
                sourceCell = cli.readCell(board, I18n.string(I18nKey.WHICH_WORKER_DO_YOU_WANT_TO_MOVE));
            }
            sourceCellX = sourceCell.getX();
            sourceCellY = sourceCell.getY();
            sourceCell.getWorker().ifPresentOrElse(
                    worker -> {
                        if (worker.getPlayer().getUser().equals(client.getCurrentActiveUser())) {
                            if (turn.getAllowedWorkers().contains(worker.getWorkerID())) {
                                workerID = worker.getWorkerID();
                            }
                            else {
                                cli.error(I18n.string(I18nKey.YOU_CANT_MOVE_THE_SPECIFIED_WORKER));
                            }
                        }
                        else {
                            cli.error(I18n.string(I18nKey.CHOOSE_ONE_OF_YOUR_WORKERS));
                        }
                    },
                    () -> cli.error(I18n.string(I18nKey.CHOOSE_ONE_OF_YOUR_WORKERS))
            );
        }
        return false;
    }

    /**
     * Obtains from the user the target cell of the movement they want to perform.
     *
     * @param turn  the current turn
     * @param board the board of the game
     * @return whether this turn should end immediately
     */
    private boolean getTargetCell(ReducedTurn turn, ReducedBoard board) {
        ReducedCell targetCell;
        if (!turn.isSkippable() && workerWasForced) {
            targetCell = cli.readCell(board, I18n.string(I18nKey.WHERE_DO_YOU_WANT_TO_PLACE_YOUR_WORKER));
        }
        else {
            targetCell = cli.readCell(board, String.format("%s (%s)", I18n.string(I18nKey.WHERE_DO_YOU_WANT_TO_PLACE_YOUR_WORKER), I18n.string(workerWasForced ? I18nKey.X_TO_SKIP : I18nKey.X_TO_CANCEL)), true);
        }
        board.getTargets(turn.getWorkerWalkableCells(workerID)).forEach(
                targetedCell -> targetedCell.setHighlighted(false)
        );
        if (targetCell == null) {
            workerID = null;
            if (workerWasForced) {
                clientState.notifyUiInteraction();
            }
            else {
                client.requestRender();
            }
            return true;
        }
        targetCellX = targetCell.getX();
        targetCellY = targetCell.getY();
        return false;
    }

    /**
     * Renders the move CLI client turn state for the non-currently active players.
     */
    private void renderCurrentlyInactive() {
        cli.println(String.format(I18n.string(I18nKey.WAIT_FOR_S_TO_PERFORM_THEIR_MOVE), client.getCurrentActiveUser().nickname));
    }

}
