package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.actions.MoveAction;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.TargetCells;
import it.polimi.ingsw.model.turnevents.TurnEvents;

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
            Worker[] workers = turn.getPlayer().getOwnWorkers();
            for (Worker worker : workers) {
                Cell currentCell = worker.getCell();
                //TODO
//                TargetCells walkableCells = turn.getWalkableCells(worker);
//                TargetCells surroundingCells = TargetCells.fromCellAndRadius(worker.getCell(), 1);
//                for (Cell targetCell : surroundingCells.getTargets()) {
//                    targetCell.getWorker().ifPresent(targetWorker -> {
//                        if (targetCell.isWalkable() && Cell.computeHeightDifference(targetCell, currentCell) <= 1 && !targetWorker.getPlayer().equals(turn.getPlayer())) {
//                            walkableCells.addTargets(targetCell);
//                        }
//                    });
//                }
            }
        }

        @Override
        protected void onAfterMovement(Turn turn) {
            List<MoveAction> moveActions = turn.getMoves();
            //TODO: shall we check there is at least 1 move? After all, if we got here, there at least 1 move was performed
            MoveAction lastMove = moveActions.get(moveActions.size() - 1);
            lastMove.getTargetCell().getWorker().ifPresent(targetWorker -> {
                if (!targetWorker.getPlayer().equals(turn.getPlayer())) {
//                    TODO
//                    game.setWorkerCell(targetWorker, lastAction.sourceCell);
                }
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

