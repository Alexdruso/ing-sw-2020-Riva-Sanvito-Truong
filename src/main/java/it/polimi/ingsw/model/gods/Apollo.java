package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.actions.MoveAction;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.TargetCells;
import it.polimi.ingsw.model.turnevents.TurnEvents;
import it.polimi.ingsw.model.workers.Worker;

import java.util.List;

/**
 * The god card Apollo.
 */
class Apollo extends AbstractGod {
    /**
     * The TurnEvents for the owner of the Apollo god card.
     */
    private static final TurnEvents ownerTurnEvents = new TurnEvents(){
        @Override
        protected void onBeforeMovement(Turn turn) {
            Worker[] workers = turn.getPlayer().getWorkers();
            for (Worker worker : workers) {
                Cell currentCell = worker.getCell();
                TargetCells walkableTargets = turn.getWorkerWalkableCells(worker);
                TargetCells surroundingTargets = TargetCells.fromCellAndRadius(worker.getCell(), 1);
                List<Cell> surroundingCells = turn.getGame().getBoard().getTargets(surroundingTargets);
                for (Cell targetCell : surroundingCells) {
                    targetCell.getWorker().ifPresent(targetWorker -> {
                        if (
                                !targetCell.getTower().isComplete()
                                        && targetCell.getTower().getCurrentLevel() - currentCell.getTower().getCurrentLevel() <= 1
                                        && !targetWorker.getPlayer().equals(turn.getPlayer())
                        ) {
                            walkableTargets.setPosition(targetCell, true);
                        }
                    });
                }
            }
        }

        @Override
        protected void onAfterMovement(Turn turn) {
            List<MoveAction> moveActions = turn.getMoves();
            //TODO: shall we check there is at least 1 move? After all, if we got here, there at least 1 move was performed
            MoveAction lastMove = moveActions.get(moveActions.size() - 1);
            lastMove.getTargetCell().getWorker().ifPresent(targetWorker -> {
                // already checked in onBeforeMovement if (!targetWorker.getPlayer().equals(turn.getPlayer())) {
                turn.getGame().setWorkerCell(targetWorker, lastMove.getSourceCell());
                // already checked in onBeforeMovement }
            });
        }
    };

    @Override
    public String getName() {
        return "Apollo";
    }

    /**
     * Gets the TurnEvents for the player owning Apollo.
     *
     * @return the TurnEvents for the player owning Apollo
     */
    @Override
    public TurnEvents getOwnerTurnEvents() {
        return ownerTurnEvents;
    }

}

