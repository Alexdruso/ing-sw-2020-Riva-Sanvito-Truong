package it.polimi.ingsw.model.board;

/**
 * A Block object represents a building block that composes a Tower
 */
class Block implements Buildable{

    /**
     * The Block instance
     */
    private static Block instance;

    /**
     * Class constructor.
     * This method is private as the instance should only be created from the Component enum
     */
    private Block(){};

    protected static Block getInstance(){
        if(instance == null){
            instance = new Block();
        }
        return instance;
    }

    /**
     * This method represents whether a worker can walk or build on this block.
     * For a Block object this always returns true.
     */
    @Override
    public Boolean isTargetable() {
        return true;
    }
}
