package it.polimi.ingsw.model;

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
     * This method just builds the worker without any cell
     */
    public Worker(){

    }

    /**
     * This method sets a new position for the worker.
     * It must be called at least once
     * @param cell the cell we want to move the player to
     */
    public void setCell(Cell cell){
        this.cell = cell;
    }

    /**
     * This methods returns the worker's cell
     * @return the worker's cell
     */
    public Cell getCell(){
        return this.cell;
    }
}
