package it.polimi.ingsw.model.board;

/**
 * This enum represents all the possible objects that can be built by a worker.
 * Each enum instance provides a getInstance() method to retrieve an instance of the
 * relative class.
 */
public enum Component {
    BLOCK(new Block()),
    DOME(new Dome());

    private final Buildable buildable;

    Component(Buildable buildable){
        this.buildable = buildable;
    }

    public Buildable getInstance(){
        return buildable;
    }

}
