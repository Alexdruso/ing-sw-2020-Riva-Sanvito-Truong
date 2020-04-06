package it.polimi.ingsw.model.board;

/**
 * A Block object represents a building block that composes a Tower.
 */
class Dome implements Buildable {

    /**
     * The Dome instance
     */
    private static Dome instance;

    /**
     * Class constructor.
     * This method is private as the instance should only be created from the Component enum.
     */
    private Dome(){};

    /**
     * This method retrieves the singleton instance of the Dome
     * @return the Dome instance
     */
    protected static Dome getInstance(){
        if(instance == null){
            instance = new Dome();
        }
        return instance;
    }

    /**
     * This method represents whether a worker can walk or build on this block.
     * For a Block object this always returns true.
     */
    @Override
    public Boolean isTargetable() {
       return false;
    }
}
