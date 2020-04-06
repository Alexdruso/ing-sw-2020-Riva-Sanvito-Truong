package it.polimi.ingsw.utils.playercommands;

import it.polimi.ingsw.model.workers.WorkerID;

/**
 * This class represents a command given by the player to move a worker to another cell.
 */
public class PlayerMoveCommand extends PlayerCommand {
    /**
     * The x coordinate of the cell from which the worker moved
     */
    public final int sourceCellX;

    /**
     * The y coordinate of the cell from which the worker moved
     */
    public final int sourceCellY;

    /**
     * The x coordinate of the cell to which the worker moved
     */
    public final int targetCellX;

    /**
     * The y coordinate of the cell to which the worker moved
     */
    public final int targetCellY;

    /**
     * The worker who performed the move
     */
    public final WorkerID performer;

    /**
     * Constructor, stores all the variables by reference
     * @param sourceCellX The x coordinate of the cell from which the worker moved
     * @param sourceCellY The y coordinate of the cell from which the worker moved
     * @param targetCellX The x coordinate of the cell to which the worker moved
     * @param targetCellY The y coordinate of the cell to which the worker moved
     * @param performer The worker who performed the move
     */
    public PlayerMoveCommand(int sourceCellX, int sourceCellY, int targetCellX,
                             int targetCellY, WorkerID performer) {
        this.sourceCellX = sourceCellX;
        this.sourceCellY = sourceCellY;
        this.targetCellX = targetCellX;
        this.targetCellY = targetCellY;
        this.performer = performer;
    }

    /**
     * This method returns the type of the current action
     * @return the type of the current action, as an instance of PlayerActions
     */
    @Override
    public PlayerCommands getActionType() {
        return PlayerCommands.MOVE;
    }
}
