package it.polimi.ingsw.server.model.workers;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.board.Cell;

import java.util.Objects;

/**
 * This class is the worker, a pawn of the player.
 * It must be place on a tile.
 * It has knowledge of the tile it is placed upon.
 */
public class Worker {

    /**
     * The cell the player is in, it must be unique at every moment of the game
     */
    private Cell cell;

    /**
     * The player who owns this worker.
     */
    private final Player player;

    /**
     * The worker ID
     */
    private final WorkerID workerID;

    /**
     * This method just builds the worker without any cell
     *
     * @param player   the player who owns this worker
     * @param workerID the worker ID
     */
    public Worker(Player player, WorkerID workerID){
        this.player = player;
        this.workerID = workerID;
    }

    /**
     * This method sets a new position for the worker.
     * It must be called at least once
     *
     * @param cell the cell we want to move the player to
     */
    public void setCell(Cell cell){
        this.cell = cell;
    }

    /**
     * This methods returns the worker's cell
     *
     * @return the worker's cell
     */
    public Cell getCell(){
        return this.cell;
    }

    /**
     * Gets the player who owns the worker.
     *
     * @return the player who owns the worker
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the Worker ID
     *
     * @return the Worker ID
     */
    public WorkerID getWorkerID() {
        return workerID;
    }

    /**
     * Overridden equals method
     *
     * @param o the object to compare
     * @return true if this equals object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Worker worker = (Worker) o;
        return getPlayer().equals(worker.getPlayer()) &&
                getWorkerID() == worker.getWorkerID();
    }

    /**
     * Overridden hashcode method
     *
     * @return hashed value of worker
     */
    @Override
    public int hashCode() {
        return Objects.hash(getPlayer(), getWorkerID());
    }
}
