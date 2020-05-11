package it.polimi.ingsw.model.actions;

import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.workers.Worker;

public class MoveAction implements Action{

    /**
     * The cell from which the worker moved
     */
    private final Cell sourceCell;

    /**
     * The cell to which the worker moved
     */
    private final Cell targetCell;

    /**
     * The level of the cell from which the worker moved
     */
    private final int sourceLevel;

    /**
     * The level of the cell to which the worker moved
     */
    private final int targetLevel;

    /**
     * The worker who performed the move
     */
    private Worker worker;

    /**
     * Constructor, stores all the variables by reference
     *
     * @param sourceCell  The cell from which the worker moved
     * @param targetCell  The cell to which the worker moved
     * @param sourceLevel The level of the cell from which the worker moved
     * @param targetLevel The level of the cell to which the worker moved
     * @param worker      The worker who performed the move
     */
    public MoveAction(Cell sourceCell, Cell targetCell, int sourceLevel, int targetLevel, Worker worker) {
        this.sourceCell = sourceCell;
        this.targetCell = targetCell;
        this.sourceLevel = sourceLevel;
        this.targetLevel = targetLevel;
        this.worker = worker;
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
    public Worker getWorker() {
        return worker;
    }
}
