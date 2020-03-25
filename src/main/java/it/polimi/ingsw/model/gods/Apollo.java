package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.turnevents.TurnEvents;

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
            //TODO
            /*
            Cell currentCell = worker.getCell();
            TargetCells walkableCells = worker.getWalkableCells();
            TargetCells surroundingCells = TargetCells.fromCellAndRadius(worker.getCell(), 1);
            for (Cell targetCell : surroundingCells.getTargets()) {
                targetCell.getWorker().ifPresent(targetWorker -> {
                    if (targetCell.isWalkable() && Cell.computeHeightDifference(targetCell, currentCell) <= 1 && !targetWorker.getPlayer().equals(turn.getPlayer())) {
                        walkableCells.addTargets(targetCell);
                    }
                });
            }
             */
        }

        @Override
        protected void onAfterMovement(Turn turn) {
            //TODO
            /*
            try {
                MoveAction lastAction = (MoveAction) turn.getAction(-1);
                lastAction.targetCell.getWorker().ifPresent(targetWorker -> {
                    if (!targetWorker.getPlayer().equals(turn.getPlayer())) {
                        game.setWorkerCell(targetWorker, lastAction.sourceCell);
                    }
                });
            }
            catch (ClassCastException e) {
                //TODO
            }
             */
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

