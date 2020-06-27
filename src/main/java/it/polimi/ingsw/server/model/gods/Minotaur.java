package it.polimi.ingsw.server.model.gods;

import it.polimi.ingsw.server.model.Turn;
import it.polimi.ingsw.server.model.actions.MoveAction;
import it.polimi.ingsw.server.model.board.Cell;
import it.polimi.ingsw.server.model.board.Direction;
import it.polimi.ingsw.server.model.board.TargetCells;
import it.polimi.ingsw.server.model.turnevents.TurnEvents;
import it.polimi.ingsw.server.model.workers.Worker;

/**
 * The god card Minotaur.
 */
class Minotaur extends AbstractGod {
    /**
     * The TurnEvents for the owner of the Minotaur god card.
     */
    private static final TurnEvents ownerTurnEvents = new TurnEvents() {
        @Override
        protected void onBeforeMovement(Turn turn) {
            for (Worker worker : turn.getPlayer().getWorkers()) {
                Cell currentCell = worker.getCell();
                TargetCells walkableTargets = turn.getWorkerWalkableCells(worker);
                TargetCells surroundingTargets = TargetCells.fromCellAndRadius(currentCell, 1);
                for (Cell targetCell : turn.getGame().getBoard().getTargets(surroundingTargets)) {
                    targetCell.getWorker().ifPresent(targetWorker -> {
                        if (
                                !targetCell.getTower().isComplete()
                                        && targetCell.getTower().getCurrentLevel() - currentCell.getTower().getCurrentLevel() <= 1
                                        && !targetWorker.getPlayer().equals(turn.getPlayer())
                        ) {
                            turn.getGame().getBoard().fromBaseCellAndDirection(
                                    targetCell, new Direction(currentCell, targetCell)
                            ).ifPresent(pushbackCell -> {
                                if (!pushbackCell.getTower().isComplete() && pushbackCell.getWorker().isEmpty()) {
                                    walkableTargets.setPosition(targetCell, true);
                                }
                            });
                        }
                    });
                }
            }
        }

        @Override
        protected void onAfterMovement(Turn turn) {
            MoveAction lastMove = turn.getMoves().get(0);
            lastMove.getTargetCell().getWorker().ifPresent(targetWorker -> {
                if (!targetWorker.getPlayer().equals(turn.getPlayer())) {
                    turn.getGame().setWorkerCell(
                            targetWorker,
                            turn.getGame().getBoard().fromBaseCellAndDirection(
                                    lastMove.getTargetCell(), new Direction(lastMove.getSourceCell(), lastMove.getTargetCell())
                            ).get()
                    );
                }
            });
        }
    };

    /**
     * Gets the god's name.
     *
     * @return the name
     */
    @Override
    public String getName() {
        return "Minotaur";
    }

    /**
     * Gets the TurnEvents for the player owning Minotaur.
     *
     * @return the TurnEvents for the player owning Minotaur
     */
    @Override
    public TurnEvents getOwnerTurnEvents() {
        return ownerTurnEvents;
    }

}
