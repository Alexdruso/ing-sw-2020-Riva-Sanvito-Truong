package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.workers.Worker;

import java.util.Optional;

/**
 * This class represents a cell on the board. A cell may contain a worker and always contains
 * a Tower object.
 */
public class Cell {
    /**
     * The X.
     */
    public final int x;
    /**
     * The Y.
     */
    public final int y;

    /**
     * The Tower.
     */
    private Tower tower;

    private Optional<Worker> worker;

    /**
     * Class constructor
     *
     * @param x horizontal coordinate of the cell on the board
     * @param y vertical coordinate of the cell on the board
     */
    public Cell(int x, int y){
        tower = new Tower();
        this.x = x;
        this.y = y;
        worker = Optional.empty();
    }

    /**
     * This method return the Tower instance that is placed on the cell
     *
     * @return the Tower instance
     */
    public Tower getTower(){
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

}
