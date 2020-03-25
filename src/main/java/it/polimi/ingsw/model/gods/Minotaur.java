package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.turnevents.TurnEvents;

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
            //TODO
            /*
            Cell currentCell = worker.getCell();
            TargetCells walkableCells = worker.getWalkableCells();
            TargetCells surroundingCells = TargetCells.fromCellAndRadius(worker.getCell(), 1);
            for (Cell targetCell : surroundingCells.getTargets()) {
                targetCell.getWorker().ifPresent(targetWorker -> {
                    if (targetCell.isWalkable() && Cell.computeHeightDifference(targetCell, currentCell) <= 1 && !targetWorker.getPlayer().equals(turn.getPlayer())) {
                        board.getCellFromCellAndDelta(targetCell, Cell.computeDelta(targetCell, currentCell)).ifPresent(pushbackCell -> {
                            if (pushbackCell.isWalkable() && !pushbackCell.getWorker().isPresent()) {
                                walkableCells.addTargets(targetCell);
                            }
                        });
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
                //TODO: ensure we have a reference to the previous worker that occupied the cell; maybe we can move the current playing worker AFTER we process the TurnEvents
                MoveAction lastAction = (MoveAction) turn.getAction(-1);
                lastAction.targetCell.getWorker().ifPresent(targetWorker -> {
                    if (!targetWorker.getPlayer().equals(turn.getPlayer())) {
                        game.setWorkerCell(targetWorker, board.getCellFromCellAndDelta(lastAction.targetCell, Cell.computeDelta(lastAction.targetCell, lastAction.sourceCell)));
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
