package it.polimi.ingsw.server.model.board;

/**
 * A Block object represents a building block that composes a Tower.
 */
class Dome implements Buildable {
    /**
     * Class constructor.
     * This method is private as the instance should only be created from the Component enum.
     */
    Dome(){}

    /**
     * This method represents whether a worker can walk or build on this block.
     * For a Block object this always returns true.
     *
     * @return true if the dome is targetable
     */
    @Override
    public boolean isTargetable() {
       return false;
    }
}
