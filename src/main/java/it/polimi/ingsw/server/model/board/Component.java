package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.utils.networking.transmittables.ReducedComponent;

/**
 * This enum represents all the possible objects that can be built by a worker.
 * Each enum instance provides a getInstance() method to retrieve an instance of the
 * relative class.
 */
public enum Component {
    /**
     * The Block.
     */
    BLOCK(new Block()),
    /**
     * The Dome.
     */
    DOME(new Dome());

    /**
     * The Buildable.
     */
    private final Buildable buildable;

    /**
     * Instantiates a new Component.
     *
     * @param buildable the buildable
     */
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
