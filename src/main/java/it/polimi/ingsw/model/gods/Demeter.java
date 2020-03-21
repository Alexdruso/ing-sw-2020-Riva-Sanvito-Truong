package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.turnevents.TurnEvents;

/**
 * The god card Demeter.
 */
class Demeter extends AbstractGod {
    /**
     * The TurnEvents for the owner of the Demeter god card.
     */
    private static final TurnEvents ownerTurnEvents = new TurnEvents() {
        @Override
        protected void onBeforeBuild() {
            //TODO
        }
    };

    @Override
    public String getName() {
        return "Demeter";
    }

    /**
     * Gets the TurnEvents for the player owning Demeter.
     *
     * @return the TurnEvents for the player owning Demeter
     */
    @Override
    public TurnEvents getOwnerTurnEvents() {
        return ownerTurnEvents;
    }

}
