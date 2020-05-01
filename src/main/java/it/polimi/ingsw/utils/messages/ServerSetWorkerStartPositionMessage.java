package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.workers.WorkerID;
import it.polimi.ingsw.utils.networking.ClientHandleable;

public class ServerSetWorkerStartPositionMessage implements ServerMessage, ClientHandleable {
    public final ReducedUser user;
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
     *
     * @param user        the user performing the set position
     * @param targetCellX The x coordinate of the cell to which the worker is positioned
     * @param targetCellY The y coordinate of the cell to which the worker is positioned
     * @param performer   The positioned worker
     */
    public ServerSetWorkerStartPositionMessage(ReducedUser user, int targetCellX, int targetCellY, WorkerID performer) {
        this.user = user;
        this.targetCellX = targetCellX;
        this.targetCellY = targetCellY;
        this.performer = performer;
    }

    @Override
    public boolean handleTransmittable(Client client) {
        return false;
    }
}
