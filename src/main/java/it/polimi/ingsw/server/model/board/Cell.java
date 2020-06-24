package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.model.workers.Worker;

import java.util.Optional;

/**
 * This class represents a cell on the board. A cell may contain a worker and always contains
 * a Tower object.
 */
public class Cell {
    /**
     * The X.
     */
    private final int x;
    /**
     * The Y.
     */
    private final int y;

    /**
     * The Tower.
     */
    private final Tower tower;

    /**
     * The worker
     */
    private Optional<Worker> worker;

    /**
     * Class constructor
     *
     * @param x horizontal coordinate of the cell on the board
     * @param y vertical coordinate of the cell on the board
     */
    public Cell(int x, int y) {
        tower = new Tower();
        this.x = x;
        this.y = y;
        worker = Optional.empty();
    }

    /**
     * Gets x coordinate.
     *
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets y coordinate.
     *
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * This method return the Tower instance that is placed on the cell
     *
     * @return the Tower instance
     */
    public Tower getTower() {
        return tower;
    }


    /**
     * Gets the worker that occupies the cell, if an.
     *
     * @return the worker, if present; otherwise, Optional.empty()
     */
    public Optional<Worker> getWorker() {
        return worker;
    }

    /**
     * Assign a worker to this cell.
     *
     * @param worker the worker to assign to this cell
     */
    public void setWorker(Worker worker) {
        this.worker = Optional.of(worker);
    }

    /**
     * Sets this cell as not occupied by any worker.
     */
    public void setNoWorker() {
        this.worker = Optional.empty();
    }

    /**
     * Computes the height difference between two towers. The difference is taken by calculating the height of other
     * minus the height of this. The towers must not be complete
     * (i.e. They must not have a Dome on top)
     *
     * @param other the other Cell with which to compute the difference
     * @return an int representing the height difference.
     */
    public int getHeightDifference(Cell other){
        return other.getTower().getCurrentLevel() - this.getTower().getCurrentLevel();
    }

}
