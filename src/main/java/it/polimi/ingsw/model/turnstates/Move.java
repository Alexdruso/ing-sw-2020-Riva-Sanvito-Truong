package it.polimi.ingsw.model.turnstates;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.workers.Worker;

public class Move extends TurnState {

    /**
     * This method sets things up before we can use the other methods provided by the state
     *
     * @param turn the Context
     */
    @Override
    public void setup(Turn turn) {
        super.setup(turn);
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
        return super.canMoveTo(pawn, targetCell, turn);
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
        super.moveTo(pawn, targetCell, turn);
    }
}
