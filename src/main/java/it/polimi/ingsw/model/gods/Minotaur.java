package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.turnevents.TurnEvents;

/**
 * The god card Minotaur.
 */
class Minotaur extends AbstractGod {
    /**
     * The TurnEvents for the owner of the Minotaur god card.
     */
    class OwnerTurnEvents extends TurnEvents {
        @Override
        protected void onTurnStart() {
            //TODO
        }

        @Override
        protected void onAfterMovement() {
            //TODO
        }
    }

    @Override
    public String getName() {
        return "Minotaur";
    }

    @Override
    public TurnEvents getOwnerTurnEvents() {
        return new OwnerTurnEvents();
    }

}
