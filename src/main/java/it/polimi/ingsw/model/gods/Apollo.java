package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.turnevents.TurnEvents;

/**
 * The god card Apollo.
 */
class Apollo extends AbstractGod {
    /**
     * The TurnEvents for the owner of the Apollo god card.
     */
    private static final TurnEvents ownerTurnEvents = new TurnEvents(){
        @Override
        protected void onBeforeMovement() {
            //TODO
        }

        @Override
        protected void onAfterMovement() {
            //TODO
        }
    };

    @Override
    public String getName() {
        return "Apollo";
    }

    /**
     * Gets the TurnEvents for the player owning Apollo.
     *
     * @return the TurnEvents for the player owning Apollo
     */
    @Override
    public TurnEvents getOwnerTurnEvents() {
        return ownerTurnEvents;
    }

}

