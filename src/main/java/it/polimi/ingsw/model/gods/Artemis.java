package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.turnevents.TurnEvents;

/**
 * The god card Artemis.
 */
class Artemis extends AbstractGod {
    /**
     * The TurnEvents for the owner of the Artemis god card.
     */
    private static final TurnEvents ownerTurnEvents = new TurnEvents(){
        @Override
        protected void onTurnStart() {
            //TODO
        }

        @Override
        protected void onAfterMovement() {
            //TODO
        }
    };

    @Override
    public String getName() {
        return "Artemis";
    }

    /**
     * Gets the TurnEvents for the player owning Artemis.
     *
     * @return the TurnEvents for the player owning Artemis
     */
    @Override
    public TurnEvents getOwnerTurnEvents() {
        return ownerTurnEvents;
    }

}
