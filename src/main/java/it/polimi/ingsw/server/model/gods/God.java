package it.polimi.ingsw.server.model.gods;

import it.polimi.ingsw.server.model.turnevents.TurnEvents;

/**
 * The interface of a god.
 * For each god, it allows to specify a set of hooks that need to be attached either to the current player or to all his opponents.
 *
 * @see TurnEvents
 */
public interface God {
    /**
     * Gets the god's name.
     *
     * @return the name
     */
    String getName();

    /**
     * Gets the TurnEvents to be applied to the player owning the god.
     *
     * @return the owner turn events
     */
    TurnEvents getOwnerTurnEvents();

    /**
     * Gets the TurnEvents to be applied to all the opponents of the player owning the god.
     *
     * @return the opponents turn events
     */
    TurnEvents getOpponentsTurnEvents();
}
