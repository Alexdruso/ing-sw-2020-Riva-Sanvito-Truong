package it.polimi.ingsw.model.actions;

import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.board.Cell;

public class MoveAction implements Action{

    /**
     * The cell from which the worker moved
     */
    private Cell sourceCell;

    /**
     * The cell to which the worker moved
     */
    private Cell targetCell;

    /**
     * The level of the cell from which the worker moved
     */
    private int sourceLevel;

    /**
     * The level of the cell to which the worker moved
     */
    private int targetLevel;

    /**
     * The worker who performed the move
     */
    private Worker performer;

    /**
     * Constructor, stores all the variables by reference
     * @param sourceCell The cell from which the worker moved
     * @param targetCell The cell to which the worker moved
     * @param sourceLevel The level of the cell from which the worker moved
     * @param targetLevel The level of the cell to which the worker moved
     * @param performer The worker who performed the move
     */
    public MoveAction(Cell sourceCell, Cell targetCell, int sourceLevel, int targetLevel, Worker performer) {
        this.sourceCell = sourceCell;
        this.targetCell = targetCell;
        this.sourceLevel = sourceLevel;
        this.targetLevel = targetLevel;
        this.performer = performer;
    }

    /**
     * @return The cell from which the worker moved
     */
    public Cell getSourceCell() {
        return sourceCell;
    }

    /**
     * @return The cell to which the worker moved
     */
    public Cell getTargetCell() {
        return targetCell;
    }

    /**
     * @return The level of the cell from which the worker moved
     */
    public int getSourceLevel() {
        return sourceLevel;
    }

    /**
     * @return The level of the cell to which the worker moved
     */
    public int getTargetLevel() {
        return targetLevel;
    }

    /**
     * @return The worker who performed the move
     */
    public Worker getPerformer() {
        return performer;
    }
}
