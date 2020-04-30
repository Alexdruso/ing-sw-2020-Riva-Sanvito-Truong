package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.turnevents.TurnEvents;
import it.polimi.ingsw.utils.messages.ReducedGod;

/**
 * The base representation of a god card.
 * It needs to be extended by each one of the actual god cards.
 * For each god, it allows to specify a set of hooks that need to be attached either to the current player or to all his opponents.
 *
 * @see TurnEvents
 */
abstract class AbstractGod implements God {
    /**
     * A default TurnEvents instance that applies no actions for the player owning the god
     */
    private static final TurnEvents ownerTurnEvents = new TurnEvents();

    /**
     * A default TurnEvents instance that applies no actions for the opponents of the player owning the god
     */
    private static final TurnEvents opponentsTurnEvents = new TurnEvents();

    /**
     * Gets a default TurnEvents instance that applies no actions for the player owning the god unless overridden by a subclass.
     *
     * @return the owner turn events
     */
    public TurnEvents getOwnerTurnEvents() {
        return ownerTurnEvents;
    }

    /**
     * Gets a default TurnEvents instance that applies no actions for the opponents of the player owning the god unless overridden by a subclass.
     *
     * @return the opponents turn events
     */
    public TurnEvents getOpponentsTurnEvents() {
        return opponentsTurnEvents;
    }

    public ReducedGod toReducedGod() {
        return new ReducedGod(this.getName());
    }
}
