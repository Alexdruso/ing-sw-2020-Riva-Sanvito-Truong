package it.polimi.ingsw.utils.networking.transmittables.servermessages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.networking.ClientHandleable;
import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;
import it.polimi.ingsw.utils.networking.transmittables.ReducedWorkerID;

/**
 * The type Server remove worker message.
 */
public class ServerRemoveWorkerMessage implements ServerMessage, ClientHandleable {
    /**
     * The User whose worker has been removed.
     */
    public final ReducedUser user;
    /**
     * The Worker.
     */
    public final ReducedWorkerID workerID;
    /**
     * The Cell x.
     */
    public final int cellX;
    /**
     * The Cell y.
     */
    public final int cellY;

    /**
     * Instantiates a new Server remove worker message.
     *
     * @param user     the user
     * @param workerID the worker
     * @param cellX    the cell x
     * @param cellY    the cell y
     */
    public ServerRemoveWorkerMessage(ReducedUser user, ReducedWorkerID workerID, int cellX, int cellY) {
        this.user = user;
        this.workerID = workerID;
        this.cellX = cellX;
        this.cellY = cellY;
    }

    @Override
    public boolean handleTransmittable(Client client) {
        client.getGame().removeWorker(user, workerID, cellX, cellY);
        return true;
    }
}
