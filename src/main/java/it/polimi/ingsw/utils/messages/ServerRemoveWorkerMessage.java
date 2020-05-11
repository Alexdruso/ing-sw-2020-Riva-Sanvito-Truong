package it.polimi.ingsw.utils.messages;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.networking.ClientHandleable;

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
    public final ReducedWorkerID worker;
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
     * @param user   the user
     * @param worker the worker
     * @param cellX  the cell x
     * @param cellY  the cell y
     */
    public ServerRemoveWorkerMessage(ReducedUser user, ReducedWorkerID worker, int cellX, int cellY) {
        this.user = user;
        this.worker = worker;
        this.cellX = cellX;
        this.cellY = cellY;
    }

    @Override
    public boolean handleTransmittable(Client client) {
        return false;
    }
}
