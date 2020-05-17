package it.polimi.ingsw.model.board;

import it.polimi.ingsw.utils.messages.ReducedComponent;

/**
 * This enum represents all the possible objects that can be built by a worker.
 * Each enum instance provides a getInstance() method to retrieve an instance of the
 * relative class.
 */
public enum Component {
    BLOCK(new Block()),
    DOME(new Dome());

    private final Buildable buildable;

    Component(Buildable buildable) {
        this.buildable = buildable;
    }

    /**
     * Get instance buildable.
     *
     * @return the buildable
     */
    public Buildable getInstance() {
        return buildable;
    }

    /**
     * Converts a ReducedComponent into a Component
     *
     * @param reducedComponent the ReducedComponent
     * @return the worker id
     */
    public static Component fromReducedComponent(ReducedComponent reducedComponent) {
        return Component.valueOf(reducedComponent.toString());
    }

    /**
     * Converts a Component into a ReducedComponent
     *
     * @return the ReducedComponent
     */
    public ReducedComponent toReducedComponent() {
        return ReducedComponent.valueOf(this.name());
    }

}
