package it.polimi.ingsw.model.turnstates;

/**
 * The enum containing all the states.
 * All states of the turn are instantiated as values of the enum TurnCreator; for this reason, it is guaranteed the existence of exactly 1 instance for each turn state at runtime.
 */
public enum TurnCreator {
    /**
     * The Start state.
     *
     * @see Start
     */
    START(new Start()),
    /**
     * The Artemis god.
     *
     * @see Move
     */
    MOVE(new Move()),
    /**
     * The Athena god.
     *
     * @see Build
     */
    BUILD(new Build()),
    /**
     * The Atlas god.
     *
     * @see End
     */
    END(new End());


    private final TurnState turnState;

    /**
     * Creator of TurnCreator
     * @param turnState the turnstate we want to initialize
     */
    TurnCreator(TurnState turnState) {
        this.turnState = turnState;
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    public TurnState getTurnState() {
        return this.turnState;
    }
}
