package it.polimi.ingsw.model.actions;

import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.board.Buildable;
import it.polimi.ingsw.model.board.Cell;

/**
 * This class implements Action interface and represents actions in which a worker builds something on a targetCell.
 * It is immutable.
 */

public class BuildAction implements  Action{
    /**
     * The cell on which the worker built
     */
    private Cell targetCell;

    /**
     * The component built on the cell
     */
    private Buildable component;

    /**
     * The level built by the worker.
     * Beware, this is the level AFTER the worker performed the build
     */
    private int builtLevel;

    /**
     * The worker who performed the build
     */
    private Worker performer;

    /**
     * Constructor, stores all the variables by reference
     * @param targetCell The cell on which the worker built
     * @param component The component built on the cell
     * @param builtLevel The level built by the worker
     * @param performer The worker who performed the build
     */
    public BuildAction(Cell targetCell, Buildable component, int builtLevel, Worker performer) {
        this.targetCell = targetCell;
        this.component = component;
        this.builtLevel = builtLevel;
        this.performer = performer;
    }

    /**
     * @return The cell on which the worker built
     */
    public Cell getTargetCell() {
        return targetCell;
    }

    /**
     * @return The component built on the cell
     */
    public Buildable getComponent() {
        return component;
    }

    /**
     * @return The level built by the worker
     */
    public int getBuiltLevel() {
        return builtLevel;
    }

    /**
     * @return The worker who performed the build
     */
    public Worker getPerformer() {
        return performer;
    }
}