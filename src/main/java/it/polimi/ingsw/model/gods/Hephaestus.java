package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.turnevents.TurnEvents;

/**
 * The god card Hephaestus.
 */
class Hephaestus extends AbstractGod {
    /**
     * The TurnEvents for the owner of the Hephaestus god card.
     */
    class OwnerTurnEvents extends TurnEvents {
        @Override
        protected void onBeforeBuild() {
            //TODO
        }
    }

    @Override
    public String getName() {
        return "Hephaestus";
    }

    @Override
    public TurnEvents getOwnerTurnEvents() {
        return new OwnerTurnEvents();
    }

}
