package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.model.workers.WorkerID;
import it.polimi.ingsw.utils.networking.ControllerHandleable;
import it.polimi.ingsw.view.View;

/**
 * This immutable class represents a command given by the player to move a worker to another cell.
 */
public class ClientMoveMessage implements ClientMessage, ControllerHandleable{
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
    public ClientMoveMessage(int sourceCellX, int sourceCellY,
                             int targetCellX, int targetCellY, WorkerID performer) {
        super();
        this.sourceCellX = sourceCellX;
        this.sourceCellY = sourceCellY;
        this.targetCellX = targetCellX;
        this.targetCellY = targetCellY;
        this.performer = performer;
    }

    @Override
    public boolean handleTransmittable(Controller handler, View view, User user) {
        handler.dispatchMoveAction(this, view, user);
        return true;
    }
}
