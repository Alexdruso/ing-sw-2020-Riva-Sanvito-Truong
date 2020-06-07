package it.polimi.ingsw.server.model.board;

/**
 * This immutable class represents a direction, which is the vector representing the displacement from a Cell to another
 */
public class Direction {
    /**
     * The displacement on the x axis
     */
    public final int dx;

    /**
     * The displacement on the y axis
     */
    public final int dy;

    /**
     * The class constructor.
     * @param firstCell
     * @param secondCell
     */
    public Direction(Cell firstCell, Cell secondCell){
        dx = secondCell.getX() - firstCell.getX();
        dy = secondCell.getY() - firstCell.getY();
    }
}
