package it.polimi.ingsw.model.playercommands;

import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.workers.Worker;

/**
 * This class represents a command given by the player to move a worker to another cell.
 */
public class PlayerMoveCommand extends PlayerCommand {
    /**
     * The cell from which the worker moved
     */
    private Cell sourceCell;

    /**
     * The cell to which the worker moved
     */
    private Cell targetCell;

    /**
     * The worker who performed the move
     */
    private Worker performer;

    /**
     * Constructor, stores all the variables by reference
     * @param sourceCell The cell from which the worker moved
     * @param targetCell The cell to which the worker moved
     * @param performer The worker who performed the move
     */
    public PlayerMoveCommand(Cell sourceCell, Cell targetCell, Worker performer) {
        this.sourceCell = sourceCell;
        this.targetCell = targetCell;
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
     * @return The worker who performed the move
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
        return PlayerCommands.MOVE;
    }
}
