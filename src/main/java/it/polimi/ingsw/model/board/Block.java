package it.polimi.ingsw.model.board;

/**
 * A Block object represents a building block that composes a Tower
 */
public class Block implements Buildable{
    /**
     * This method represents whether a worker can walk or build on this block.
     * For a Block object this always returns true.
     */
    @Override
    public Boolean isTargetable() {
        return true;
    }
}
