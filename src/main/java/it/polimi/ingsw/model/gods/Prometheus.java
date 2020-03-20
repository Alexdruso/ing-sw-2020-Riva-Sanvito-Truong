package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.turnevents.TurnEvents;

/**
 * The god card Prometheus.
 */
class Prometheus extends AbstractGod {
    /**
     * The TurnEvents for the owner of the Prometheus god card.
     */
    class OwnerTurnEvents extends TurnEvents {
        @Override
        protected void onTurnStart() {
            //TODO
        }
    }

    @Override
    public String getName() {
        return "Prometheus";
    }

    @Override
    public TurnEvents getOwnerTurnEvents() {
        return new OwnerTurnEvents();
    }

}
