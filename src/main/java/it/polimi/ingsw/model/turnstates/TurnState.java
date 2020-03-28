package it.polimi.ingsw.model.turnstates;

/**
 * The enum containing all the states.
 * All states of the turn are instantiated as values of the enum TurnCreator; for this reason, it is guaranteed the existence of exactly 1 instance for each turn state at runtime.
 */
public enum TurnState {
    /**
     * The Start state.
     *
     * @see Start
     */
    START(new Start()),
    /**
     * The Move state.
     *
     * @see Move
     */
    MOVE(new Move()),
    /**
     * The Build state.
     *
     * @see Build
     */
    BUILD(new Build()),
    /**
     * The End state.
     *
     * @see End
     */
    END(new End()),
    /**
     * The Win state.
     *
     * @see Win
     */
    WIN(new Win()),
    /**
     * The Lose state.
     *
     * @see Lose
     */
    LOSE(new Lose());

    private final AbstractTurnState abstractTurnState;

    /**
     * Creator of TurnCreator
     * @param abstractTurnState the turnstate we want to initialize
     */
    TurnState(AbstractTurnState abstractTurnState) {
        this.abstractTurnState = abstractTurnState;
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    public AbstractTurnState getTurnState() {
        return this.abstractTurnState;
    }
}
