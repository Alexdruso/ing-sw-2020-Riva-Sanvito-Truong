package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.turnevents.TurnEvents;

/**
 * The god card Athena.
 */
class Athena extends AbstractGod {
    /**
     * The TurnEvents for the owner of the Athena god card.
     */
    class OpponentsTurnEvents extends TurnEvents {
        @Override
        protected void onBeforeMovement() {
            //TODO
        }
    }

    @Override
    public String getName() {
        return "Athena";
    }

    @Override
    public TurnEvents getOpponentsTurnEvents() {
        return new OpponentsTurnEvents();
    }

}
