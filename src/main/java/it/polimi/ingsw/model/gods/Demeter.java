package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.turnevents.TurnEvents;

/**
 * The god card Demeter.
 */
class Demeter extends AbstractGod {
    /**
     * The TurnEvents for the owner of the Demeter god card.
     */
    class OwnerTurnEvents extends TurnEvents {
        @Override
        protected void onBeforeBuild() {
            //TODO
        }
    }

    @Override
    public String getName() {
        return "Demeter";
    }

    @Override
    public TurnEvents getOwnerTurnEvents() {
        return new OwnerTurnEvents();
    }

}
