package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.model.workers.WorkerID;
import it.polimi.ingsw.utils.networking.ControllerHandleable;
import it.polimi.ingsw.utils.networking.TransmittableHandler;
import it.polimi.ingsw.view.View;

public class ClientSetWorkerStartPositionMessage extends ClientMessage implements ControllerHandleable {
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
    public final WorkerID performer;

    /**
     * Constructor, stores all the variables by reference
     * @param targetCellX The x coordinate of the cell to which the worker is positioned
     * @param targetCellY The y coordinate of the cell to which the worker is positioned
     * @param performer The positioned worker
     */
    public ClientSetWorkerStartPositionMessage(int targetCellX, int targetCellY, WorkerID performer) {
        super();
        this.targetCellX = targetCellX;
        this.targetCellY = targetCellY;
        this.performer = performer;
    }

    @Override
    public boolean handleTransmittable(Controller handler, View view, User user) {
        return false;
    }

    /**
     * This method returns the type of the current action
     *
     * @return the type of the current action, as an instance of PlayerActions
     */
    @Override
    public ClientMessages getMessageType() {
        return ClientMessages.SET_WORKER_START_POSITION;
    }
}
