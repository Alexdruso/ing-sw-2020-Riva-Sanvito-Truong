package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.turnevents.TurnEvents;

/**
 * The god card Hephaestus.
 */
class Hephaestus extends AbstractGod {
    /**
     * The TurnEvents for the owner of the Hephaestus god card.
     */
    private static final TurnEvents ownerTurnEvents = new TurnEvents() {
        @Override
        protected void onBeforeBuild() {
            //TODO
        }
    };

    @Override
    public String getName() {
        return "Hephaestus";
    }

    /**
     * Gets the TurnEvents for the player owning Hephaestus.
     *
     * @return the TurnEvents for the player owning Hephaestus
     */
    @Override
    public TurnEvents getOwnerTurnEvents() {
        return ownerTurnEvents;
    }

}
