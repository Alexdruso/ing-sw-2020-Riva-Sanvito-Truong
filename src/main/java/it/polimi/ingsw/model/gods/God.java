package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.turnevents.TurnEvents;

/**
 * The interface God.
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
