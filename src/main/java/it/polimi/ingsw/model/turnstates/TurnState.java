package it.polimi.ingsw.model.turnstates;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.workers.Worker;

public abstract class TurnState {

    /**
     * This method sets things up before we can use the other methods provided by the state
     * @param turn the Context
     */
    abstract public void setup(Turn turn);

    /**
     * This method sets up the first actual state of the turn and performs
     * some default calculation on the buildableCells and walkableCells
     * @param turn the Context
     * @throws InvalidTurnStateException if not in the right state
     */
    public void startTurn(Turn turn) throws InvalidTurnStateException{
        throw new InvalidTurnStateException();
    }

    /**
     * This boolean methods checks if the pawn can move to targetCell
     * @param pawn the worker we want to move
     * @param targetCell the cell we want to move the worker to
     * @param turn the Context
     * @return if the pawn can move to targetCell
     */
    public boolean canMoveTo(Worker pawn, Cell targetCell, Turn turn){
        return false;
    }

    /**
     * This method moves the pawn to targetCell
     * @param pawn the worker we want to move
     * @param targetCell the cell we want to move the worker to
     * @param turn the Context
     * @throws InvalidTurnStateException if not in the right state
     */
    public void moveTo(Worker pawn, Cell targetCell, Turn turn) throws InvalidTurnStateException {
        throw new InvalidTurnStateException();
    }

    /**
     * This boolean methods checks if the pawn can build a Dome in targetCell
     * @param pawn the worker who performs the build
     * @param targetCell the cell involved in the build
     * @param turn the Context
     * @return true if the pawn can build dome in targetCell
     */
    public boolean canBuildDomeIn(Worker pawn, Cell targetCell, Turn turn) {
        return false;
    }

    /**
     * This methods builds a dome in targetCell
     * @param pawn the worker who performs the build
     * @param targetCell the cell involved in the build
     * @param turn the Context
     * @throws InvalidTurnStateException if not in the right state
     */
    public void buildDomeIn(Worker pawn, Cell targetCell, Turn turn) throws InvalidTurnStateException {
        throw new InvalidTurnStateException();
    }

    /**
     * This boolean methods checks if the pawn can build a block in targetCell
     * @param pawn the worker who performs the build
     * @param targetCell the cell involved in the build
     * @param turn the Context
     * @return true if the pawn can build a block in targetCell
     */
    public boolean canBuildBlockIn(Worker pawn, Cell targetCell, Turn turn){
        return false;
    }

    /**
     * This methods builds a block in targetCell
     * @param pawn the worker who performs the build
     * @param targetCell the cell involved in the build
     * @param turn the Context
     * @throws InvalidTurnStateException if not in the right state
     */
    public void buildBlockIn(Worker pawn, Cell targetCell, Turn turn) throws InvalidTurnStateException {
        throw new InvalidTurnStateException();
    }

    /**
     * This method lets the player surrender
     * @param turn the Context
     */
    public void draw(Turn turn){
        turn.setLosingTurn();
        turn.setNextState(TurnCreator.END.getTurnState());
    }

    /**
     * This method checks if we can end the turn
     * @param turn the Context
     * @return if the player can end the turn
     */
    public boolean canEndTurn(Turn turn){
        return false;
    }

    /**
     * This method ends the turn
     * @param turn the Context
     * @throws InvalidTurnStateException if not in the right state
     */
    public void endTurn(Turn turn) throws InvalidTurnStateException {
        throw new InvalidTurnStateException();
    }

}
