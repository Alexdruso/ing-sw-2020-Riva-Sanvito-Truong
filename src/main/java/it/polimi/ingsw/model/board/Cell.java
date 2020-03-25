package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Worker;

import java.util.Optional;

/**
 * This class represents a cell on the board. A cell may contain a worker and always contains
 * a Tower object.
 */
public class Cell {
    public final int x;
    public final int y;

    //TODO: [from:ANDR] why is this public?
    public Tower tower;

    private Optional<Worker> worker;

    /**
     * Class constructor
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

}
