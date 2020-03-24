package it.polimi.ingsw.model.board;

/**
 * This class represents a cell on the board. A cell may contain a worker and always contains
 * a Tower object.
 */
public class Cell {
    public final int x;
    public final int y;

    public Tower tower;

    /**
     * Class constructor
     * @param x horizontal coordinate of the cell on the board
     * @param y vertical coordinate of the cell on the board
     */
    public Cell(int x, int y){
        tower = new Tower();
        this.x = x;
        this.y = y;
    }

    /**
     * This method return the Tower instance that is placed on the cell
     * @return the Tower instance
     */
    public Tower getTower(){
        return tower;
    }
}
