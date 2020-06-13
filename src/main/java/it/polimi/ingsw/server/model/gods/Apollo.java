package it.polimi.ingsw.server.model.gods;

import it.polimi.ingsw.server.model.Turn;
import it.polimi.ingsw.server.model.actions.MoveAction;
import it.polimi.ingsw.server.model.board.Cell;
import it.polimi.ingsw.server.model.board.TargetCells;
import it.polimi.ingsw.server.model.turnevents.TurnEvents;
import it.polimi.ingsw.server.model.workers.Worker;

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
            if (moveActions.size() < 1) return;

            MoveAction lastMove = moveActions.get(moveActions.size() - 1);
            lastMove.getTargetCell().getWorker().ifPresent(
                    targetWorker -> turn.getGame().setWorkerCell(targetWorker, lastMove.getSourceCell())
            );
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

