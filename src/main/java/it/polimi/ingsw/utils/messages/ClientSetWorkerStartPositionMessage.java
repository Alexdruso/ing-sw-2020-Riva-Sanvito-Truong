package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.model.workers.WorkerID;

public class ClientSetWorkerStartPositionMessage extends ClientMessage {
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
