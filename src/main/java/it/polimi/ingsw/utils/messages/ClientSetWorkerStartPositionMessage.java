package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.utils.networking.ControllerHandleable;
import it.polimi.ingsw.view.View;

/**
 * The type Client set worker start position message.
 */
public class ClientSetWorkerStartPositionMessage implements ClientMessage, ControllerHandleable {
    /**
     * The x coordinate of the cell to which the worker is positioned
     */
    public final int targetCellX;

    /**
     * The y coordinate of the cell to which the worker is positioned
     */
    public final int targetCellY;

    /**
     * The positioned worker
     */
    public final ReducedWorkerID workerID;

    /**
     * Constructor, stores all the variables by reference
     *
     * @param targetCellX The x coordinate of the cell to which the worker is positioned
     * @param targetCellY The y coordinate of the cell to which the worker is positioned
     * @param workerID    The positioned worker
     */
    public ClientSetWorkerStartPositionMessage(int targetCellX, int targetCellY, ReducedWorkerID workerID) {
        super();
        this.targetCellX = targetCellX;
        this.targetCellY = targetCellY;
        this.workerID = workerID;
    }

    @Override
    public boolean handleTransmittable(Controller handler, View view, User user) {
        handler.dispatchSetWorkerStartPositionAction(this, view, user);
        return true;
    }
}
