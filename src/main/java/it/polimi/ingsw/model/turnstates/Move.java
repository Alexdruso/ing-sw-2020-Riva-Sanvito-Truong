package it.polimi.ingsw.model.turnstates;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.actions.MoveAction;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.TargetCells;
import it.polimi.ingsw.model.workers.Worker;

class Move extends AbstractTurnState {

    /**
     * This method sets things up before we can use the other methods provided by the state
     *
     * @param turn the Context
     */
    @Override
    public void setup(Turn turn) {
        //Sets default next state
        turn.setNextState(TurnState.BUILD.getTurnState());
        setupDefaultAllowedWorkers(turn);

        //for every allowed worker, intializes a target cell with the radius minus blocked cells
        for(Worker allowedWorker : turn.getAllowedWorkers()){
            TargetCells walkableCellsRadius = TargetCells.fromCellAndRadius(allowedWorker.getCell(), 1);
            //TargetCells nonWalkableCells = (new TargetCells()).setAllTargets(true);

            turn.getGame().getBoard().getTargets(walkableCellsRadius).
                            stream().
                            filter(cell -> cell.getTower().isComplete() || cell.getWorker().isPresent() || allowedWorker.getCell().getHeightDifference(cell) > 1).
                            forEach(cell -> walkableCellsRadius.setPosition(cell,false));

            turn.setWorkerWalkableCells(allowedWorker, walkableCellsRadius);
        }

        //compute lose conditions
        if (
                !turn.isSkippable()  //see if turn can't be skipped
                        && turn.getAllowedWorkers().stream().map(
                                allowedWorker -> turn.getGame().getBoard().getTargets(
                                        turn.getWorkerWalkableCells(allowedWorker)
                                )
                                .isEmpty() //check if worker can move to some cells
                        )
                        .reduce(true, (isNoActionAll, isNoAction) -> isNoActionAll && isNoAction)
        ) {
            turn.triggerLosingTurn(); //sets the turn to losing turn
        }
        else {
            turn.getPlayer().getTurnEventsManager().processBeforeMovementEvents(turn);
            // notify();
        }
    }

    /**
     * This boolean methods checks if the pawn can move to targetCell
     *
     * @param pawn       the worker we want to move
     * @param targetCell the cell we want to move the worker to
     * @param turn       the Context
     * @return if the pawn can move to targetCell
     */
    @Override
    public boolean canMoveTo(Worker pawn, Cell targetCell, Turn turn) {
        return turn.getWorkerWalkableCells(pawn).getPosition(targetCell.getX(),targetCell.getY());
    }

    /**
     * This method moves the pawn to targetCell
     *
     * @param pawn       the worker we want to move
     * @param targetCell the cell we want to move the worker to
     * @param turn       the Context
     */
    @Override
    public void moveTo(Worker pawn, Cell targetCell, Turn turn) {
        turn.addPerformedAction(
                new MoveAction(
                        pawn.getCell(), //the source cell
                        targetCell, //the target cell
                        pawn.getCell().getTower().getCurrentLevel(), //the source cell level
                        targetCell.getTower().getCurrentLevel(), //the target cell level
                        pawn //the performer
                )
        );

        turn.getPlayer().getTurnEventsManager().processAfterMovementEvents(turn);

        turn.getGame().setWorkerCell(pawn, targetCell);
    }
}
