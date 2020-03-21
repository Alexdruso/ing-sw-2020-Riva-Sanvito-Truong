package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.turnevents.TurnEvents;

/**
 * The god card Athena.
 */
class Athena extends AbstractGod {
    /**
     * The TurnEvents for the opponents of the owner of the Athena god card.
     */
    private static final TurnEvents opponentsTurnEvents = new TurnEvents() {
        @Override
        protected void onBeforeMovement() {
            //TODO
        }
    };

    @Override
    public String getName() {
        return "Athena";
    }

    /**
     * Gets the TurnEvents for the opponents of the player owning Athena.
     *
     * @return the TurnEvents for the opponents of the player owning Athena
     */
    @Override
    public TurnEvents getOpponentsTurnEvents() {
        return opponentsTurnEvents;
    }

}
