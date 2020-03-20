package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.turnevents.TurnEvents;

/**
 * The god card Pan.
 */
class Pan extends AbstractGod {
    /**
     * The TurnEvents for the owner of the Pan god card.
     */
    class OwnerTurnEvents extends TurnEvents {
        @Override
        protected void computeWinCondition() {
            //TODO
        }
    }

    @Override
    public String getName() {
        return "Pan";
    }

    @Override
    public TurnEvents getOwnerTurnEvents() {
        return new OwnerTurnEvents();
    }

}
