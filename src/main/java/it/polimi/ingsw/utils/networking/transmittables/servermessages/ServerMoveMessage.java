package it.polimi.ingsw.utils.networking.transmittables.servermessages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.networking.ClientHandleable;
import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;
import it.polimi.ingsw.utils.networking.transmittables.ReducedWorkerID;

/**
 * The type Server move message.
 */
public class ServerMoveMessage implements ServerMessage, ClientHandleable {
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
     * The user who performed the action
     */
    public final ReducedUser user;

    /**
     * Constructor, stores all the variables by reference
     *
     * @param user        The user
     * @param sourceCellX The x coordinate of the cell from which the worker moved
     * @param sourceCellY The y coordinate of the cell from which the worker moved
     * @param targetCellX The x coordinate of the cell to which the worker moved
     * @param targetCellY The y coordinate of the cell to which the worker moved
     * @param workerID    The worker who performed the move
     */
    public ServerMoveMessage(ReducedUser user, int sourceCellX, int sourceCellY,
                             int targetCellX, int targetCellY, ReducedWorkerID workerID) {
        this.user = user;
        this.sourceCellX = sourceCellX;
        this.sourceCellY = sourceCellY;
        this.targetCellX = targetCellX;
        this.targetCellY = targetCellY;
        this.workerID = workerID;
    }

    @Override
    public boolean handleTransmittable(Client client) {
        client.getGame().setWorkerCell(user, workerID, sourceCellX, sourceCellY, targetCellX, targetCellY);
        return true;
    }
}
