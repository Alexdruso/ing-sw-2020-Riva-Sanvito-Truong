package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.turnevents.TurnEvents;

/**
 * The god card Prometheus.
 */
class Prometheus extends AbstractGod {
    /**
     * The TurnEvents for the owner of the Prometheus god card.
     */
    private static final TurnEvents ownerTurnEvents = new TurnEvents() {
        @Override
        protected void onTurnStart() {
            //TODO
        }
    };

    @Override
    public String getName() {
        return "Prometheus";
    }

    /**
     * Gets the TurnEvents for the player owning Prometheus.
     *
     * @return the TurnEvents for the player owning Prometheus
     */
    @Override
    public TurnEvents getOwnerTurnEvents() {
        return ownerTurnEvents;
    }

}
