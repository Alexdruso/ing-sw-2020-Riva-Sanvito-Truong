package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.utils.networking.transmittables.ReducedWorkerID;

/**
 * A representation of a worker of a player, reduced with respect to the server's Worker to contain only the information required by the client.
 */
public class ReducedWorker {
    private final ReducedPlayer player;
    private final ReducedWorkerID workerID;
    private ReducedCell cell;

    /**
     * Instantiates a new Reduced worker.
     *
     * @param workerID the worker id
     * @param player   the player to which the worker belongs
     */
    public ReducedWorker(ReducedWorkerID workerID, ReducedPlayer player) {
        this.player = player;
        this.workerID = workerID;
    }

    /**
     * Gets the player to which the worker belongs.
     *
     * @return the player to which the worker belongs
     */
    public ReducedPlayer getPlayer() {
        return player;
    }

    /**
     * Gets the worker id.
     *
     * @return the worker id
     */
    public ReducedWorkerID getWorkerID() {
        return workerID;
    }

    /**
     * Gets the cell in which the worker is placed.
     *
     * @return the cell in which the worker is placed
     */
    public ReducedCell getCell() {
        return cell;
    }

    /**
     * Sets the cell in which the worker is placed.
     *
     * @param cell the cell in which the worker is placed
     */
    public void setCell(ReducedCell cell) {
        this.cell = cell;
    }
}
