package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;

import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.model.workers.WorkerID;
import it.polimi.ingsw.utils.networking.ClientHandleable;

public class ServerMoveMessage extends ServerMessage implements ClientHandleable {
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
     * Constructor, stores all the variables by reference
     * @param targetCellX The x coordinate of the cell to which the worker moved
     * @param targetCellY The y coordinate of the cell to which the worker moved
     * @param performer The worker who performed the move
     */
    public ServerMoveMessage(User user, int targetCellX, int targetCellY, WorkerID performer) {
        super(user);
        this.targetCellX = targetCellX;
        this.targetCellY = targetCellY;
        this.performer = performer;
    }

    /**
     * This method returns the type of the current action
<<<<<<< HEAD
=======
     *
>>>>>>> 1cd5d89cb42032ace9d18dc772a7cfa16f99d999
     * @return the type of the current action, as an instance of PlayerActions
     */
    @Override
    public ServerMessages getMessageType() {
        return ServerMessages.MOVE;
    }

    @Override
    public boolean handleTransmittable(Client handler) {
        return false;
    }
}
