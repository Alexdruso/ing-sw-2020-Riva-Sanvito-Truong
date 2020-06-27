package it.polimi.ingsw.server.model.actions;

import it.polimi.ingsw.server.model.board.Cell;
import it.polimi.ingsw.server.model.workers.Worker;

/**
 * The type Move action.
 */
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
    private final Worker worker;

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
     * Gets source cell.
     *
     * @return The cell from which the worker moved
     */
    public Cell getSourceCell() {
        return sourceCell;
    }

    /**
     * Gets target cell.
     *
     * @return The cell to which the worker moved
     */
    public Cell getTargetCell() {
        return targetCell;
    }

    /**
     * Gets source level.
     *
     * @return The level of the cell from which the worker moved
     */
    public int getSourceLevel() {
        return sourceLevel;
    }

    /**
     * Gets target level.
     *
     * @return The level of the cell to which the worker moved
     */
    public int getTargetLevel() {
        return targetLevel;
    }

    /**
     * Gets worker.
     *
     * @return The worker who performed the move
     */
    public Worker getWorker() {
        return worker;
    }
}
