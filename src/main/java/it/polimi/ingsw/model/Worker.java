package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.Cell;

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
    private Player player;

    /**
     * This method just builds the worker without any cell
     * @param player
     */
    public Worker(Player player){
        this.player = player;
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

    /**
     * Gets the player who owns the worker.
     *
     * @return the player who owns the worker
     */
    public Player getPlayer() {
        return player;
    }

}
