package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.turnevents.TurnEvents;

/**
 * The god card Apollo.
 */
class Apollo extends AbstractGod {
    /**
     * The TurnEvents for the owner of the Apollo god card.
     */
    class OwnerTurnEvents extends TurnEvents {
        @Override
        protected void onBeforeMovement() {
            //TODO
        }

        @Override
        protected void onAfterMovement() {
            //TODO
            System.out.println("apo");
        }
    }

    @Override
    public String getName() {
        return "Apollo";
    }

    @Override
    public TurnEvents getOwnerTurnEvents() {
        return new OwnerTurnEvents();
    }

}

