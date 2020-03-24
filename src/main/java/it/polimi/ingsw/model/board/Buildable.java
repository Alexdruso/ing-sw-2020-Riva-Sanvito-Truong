package it.polimi.ingsw.model.board;

/**
 * Interface for objects that can be placed on the board by Workers
 */
public interface Buildable {
    /**
     * This method represents whether a worker can walk or build on this block.
     */
    public Boolean isTargetable();
}
