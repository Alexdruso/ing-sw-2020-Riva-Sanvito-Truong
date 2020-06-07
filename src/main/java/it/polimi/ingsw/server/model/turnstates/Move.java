package it.polimi.ingsw.server.model.turnstates;

import it.polimi.ingsw.server.model.Turn;
import it.polimi.ingsw.server.model.actions.MoveAction;
import it.polimi.ingsw.server.model.board.Cell;
import it.polimi.ingsw.server.model.board.TargetCells;
import it.polimi.ingsw.server.model.workers.Worker;

class Move implements AbstractTurnState {

    /**
     * This method sets things up before we can use the other methods provided by the state
     *
     * @param turn the Context
     */
    public void setup(Turn turn) {
        //Sets default next state
        turn.setNextState(TurnState.BUILD.getTurnState());
        setupDefaultAllowedWorkers(turn);

        //for every worker, initializes a target cell with the radius minus blocked cells
        for (Worker worker : turn.getPlayer().getWorkers()) {
            TargetCells walkableCellsRadius = TargetCells.fromCellAndRadius(worker.getCell(), 1);

            turn.getGame().getBoard().getTargets(walkableCellsRadius)
                    .stream()
                    .filter(cell ->
                            cell.getTower().isComplete()
                                    || cell.getWorker().isPresent()
                                    || worker.getCell().getHeightDifference(cell) > 1)
                    .forEach(cell -> walkableCellsRadius.setPosition(cell, false));

            turn.setWorkerWalkableCells(worker, walkableCellsRadius);
        }
        //use powers
        turn.getPlayer().getTurnEventsManager().processBeforeMovementEvents(turn);
        //compute lose conditions
        boolean isPossibleMove = turn.getAllowedWorkers().stream()
                .map(
                        allowedWorker ->
                                !turn.getGame().getBoard()
                                        .getTargets(turn.getWorkerWalkableCells(allowedWorker))
                                        .isEmpty()
                )
                .reduce(false, (isPossibleActionAll, isPossibleAction) -> isPossibleActionAll || isPossibleAction);

        if (isPossibleMove) turn.getGame().notifyAskMove(turn); //if a move is possible, ask it
        else if (turn.isSkippable()) turn.getGame().skip(); //else if we can skip automatically, do it
        else turn.triggerLosingTurn(); //else it is a losing turn
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
        return turn.getAllowedWorkers().contains(pawn)
                && turn.getWorkerWalkableCells(pawn).getPosition(targetCell.getX(), targetCell.getY());
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
