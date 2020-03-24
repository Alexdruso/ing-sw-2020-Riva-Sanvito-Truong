package it.polimi.ingsw.model.board;

/**
 * This enum represents all the possible objects that can be built by a worker.
 * Each enum instance provides a getInstance() method to retrieve an instance of the
 * relative class.
 */
public enum Component {
    BLOCK {
        /**
         * This method creates an instance of a Block
         * @return the Block instance
         */
        public Buildable getInstance(){
            return new Block();
        }
    },
    DOME {
        /**
         * This method creates an instance of a Dome
         * @return the Dome instance
         */
        public Buildable getInstance() { return new Dome(); }
    };

    public abstract Buildable getInstance();
}
