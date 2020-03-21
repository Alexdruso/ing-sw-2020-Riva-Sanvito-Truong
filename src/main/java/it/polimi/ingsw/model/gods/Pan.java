package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.turnevents.TurnEvents;

/**
 * The god card Pan.
 */
class Pan extends AbstractGod {
    /**
     * The TurnEvents for the owner of the Pan god card.
     */
    private static final TurnEvents ownerTurnEvents = new TurnEvents() {
        @Override
        protected void computeWinCondition() {
            //TODO
        }
    };

    @Override
    public String getName() {
        return "Pan";
    }

    /**
     * Gets the TurnEvents for the player owning Pan.
     *
     * @return the TurnEvents for the player owning Pan
     */
    @Override
    public TurnEvents getOwnerTurnEvents() {
        return ownerTurnEvents;
    }

}
