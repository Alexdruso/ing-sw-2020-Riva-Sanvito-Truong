package it.polimi.ingsw.utils.networking.transmittables.clientmessages;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.User;
import it.polimi.ingsw.server.view.View;
import it.polimi.ingsw.utils.networking.ControllerHandleable;
import it.polimi.ingsw.utils.networking.transmittables.ReducedWorkerID;

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
    public final ReducedWorkerID workerID;

    /**
     * Constructor, stores all the variables by reference
     *
     * @param sourceCellX The x coordinate of the cell from which the worker moved
     * @param sourceCellY The y coordinate of the cell from which the worker moved
     * @param targetCellX The x coordinate of the cell to which the worker moved
     * @param targetCellY The y coordinate of the cell to which the worker moved
     * @param workerID    The worker who performed the move
     */
    public ClientMoveMessage(int sourceCellX, int sourceCellY,
                             int targetCellX, int targetCellY, ReducedWorkerID workerID) {
        super();
        this.sourceCellX = sourceCellX;
        this.sourceCellY = sourceCellY;
        this.targetCellX = targetCellX;
        this.targetCellY = targetCellY;
        this.workerID = workerID;
    }

    @Override
    public boolean handleTransmittable(Controller handler, View view, User user) {
        handler.dispatchMoveAction(this, view, user);
        return true;
    }
}
