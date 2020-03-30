package it.polimi.ingsw.utils.playercommands;

import it.polimi.ingsw.model.board.Buildable;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.workers.Worker;

public class PlayerBuildCommand extends PlayerCommand {
    /**
     * The cell on which the worker intends to build
     */
    private Cell targetCell;

    /**
     * The component that is to be built on the cell
     */
    private Buildable component;

    /**
     * The level that is to be built by the worker.
     */
    private int builtLevel;

    /**
     * The worker who is to perform the build
     */
    private Worker performer;

    /**
     * Constructor, stores all the variables by reference
     * @param targetCell The cell on which the worker built
     * @param component The component built on the cell
     * @param builtLevel The level built by the worker
     * @param performer The worker who performed the build
     */
    public PlayerBuildCommand(Cell targetCell, Buildable component, int builtLevel, Worker performer) {
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

    /**
     * This method returns the type of the current action
     * @return the type of the current action, as an instance of PlayerActions
     */
    @Override
    public PlayerCommands getActionType() {
        return PlayerCommands.BUILD;
    }
}
