package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.model.workers.WorkerID;
import it.polimi.ingsw.utils.networking.ClientHandleable;

public class ServerMoveMessage implements ServerMessage, ClientHandleable {
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
     * The user who performed the action
     */
    public final User user;

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
    public ServerMoveMessage(User user, int sourceCellX, int sourceCellY,
                             int targetCellX, int targetCellY, WorkerID performer) {
        this.user = user;
        this.targetCellX = targetCellX;
        this.targetCellY = targetCellY;
        this.performer = performer;
    }

    @Override
    public boolean handleTransmittable(Client handler) {
//         ReducedCell targetCell = handler.getGame().getCell(targetCellX, targetCellY);
//         handler.getGame().move(woker, sourceCell, targetCell);

//        handler.addChangedCell(sourceCell);
//        handler.addChangedCell(targetCell);

        return false;
    }
}
