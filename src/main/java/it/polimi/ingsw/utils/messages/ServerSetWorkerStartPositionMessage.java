package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.networking.ClientHandleable;

/**
 * The type Server set worker start position message.
 */
public class ServerSetWorkerStartPositionMessage implements ServerMessage, ClientHandleable {
    /**
     * The User who has to choose the worker start position.
     */
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
    public final ReducedWorkerID workerID;

    /**
     * Constructor, stores all the variables by reference
     *
     * @param user        the user performing the set position
     * @param targetCellX The x coordinate of the cell to which the worker is positioned
     * @param targetCellY The y coordinate of the cell to which the worker is positioned
     * @param workerID    The positioned worker
     */
    public ServerSetWorkerStartPositionMessage(ReducedUser user, int targetCellX, int targetCellY, ReducedWorkerID workerID) {
        this.user = user;
        this.targetCellX = targetCellX;
        this.targetCellY = targetCellY;
        this.workerID = workerID;
    }

    @Override
    public boolean handleTransmittable(Client client) {
        client.getGame().addWorker(user, workerID, targetCellX, targetCellY);
        return true;
    }
}
