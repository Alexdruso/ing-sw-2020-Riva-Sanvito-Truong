package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.workers.WorkerID;
import it.polimi.ingsw.utils.networking.ClientHandleable;
import it.polimi.ingsw.utils.networking.TransmittableHandler;

public class ServerSetWorkerStartPositionMessage extends ServerMessage implements ClientHandleable {
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
    public ServerSetWorkerStartPositionMessage(int targetCellX, int targetCellY, WorkerID performer) {
        super();
        this.targetCellX = targetCellX;
        this.targetCellY = targetCellY;
        this.performer = performer;
    }

    @Override
    public boolean handleTransmittable(Client handler) {
        return false;
    }

    /**
     * This method returns the type of the current action
     *
     * @return the type of the current action, as an instance of PlayerActions
     */
    @Override
    public ServerMessages getMessageType() {
        return ServerMessages.SET_WORKER_START_POSITION;
    }
}
