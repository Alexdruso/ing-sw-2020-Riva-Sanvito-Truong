package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.turnevents.TurnEvents;

/**
 * The god card Artemis.
 */
class Artemis extends AbstractGod {
    /**
     * The TurnEvents for the owner of the Artemis god card.
     */
    class OwnerTurnEvents extends TurnEvents {
        @Override
        protected void onTurnStart() {
            //TODO
        }

        @Override
        protected void onAfterMovement() {
            //TODO
            System.out.println("art");
        }
    }

    @Override
    public String getName() {
        return "Artemis";
    }

    @Override
    public TurnEvents getOwnerTurnEvents() {
        return new OwnerTurnEvents();
    }

}
