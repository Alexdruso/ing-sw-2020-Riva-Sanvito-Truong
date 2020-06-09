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
    public final ReducedWorkerID performer;

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
     * @param performer   The worker who performed the move
     */
    public ServerMoveMessage(ReducedUser user, int sourceCellX, int sourceCellY,
                             int targetCellX, int targetCellY, ReducedWorkerID performer) {
        this.user = user;
        this.sourceCellX = sourceCellX;
        this.sourceCellY = sourceCellY;
        this.targetCellX = targetCellX;
        this.targetCellY = targetCellY;
        this.performer = performer;
    }

    @Override
    public boolean handleTransmittable(Client client) {
         client.getGame().setWorkerCell(user, performer, sourceCellX, sourceCellY, targetCellX, targetCellY);

//        handler.addChangedCell(sourceCell);
//        handler.addChangedCell(targetCell);

        return true;
    }
}
