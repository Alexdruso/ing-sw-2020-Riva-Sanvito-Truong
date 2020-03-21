package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.turnevents.TurnEvents;

/**
 * The god card Minotaur.
 */
class Minotaur extends AbstractGod {
    /**
     * The TurnEvents for the owner of the Minotaur god card.
     */
    private static final TurnEvents ownerTurnEvents = new TurnEvents() {
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
        return "Minotaur";
    }

    /**
     * Gets the TurnEvents for the player owning Minotaur.
     *
     * @return the TurnEvents for the player owning Minotaur
     */
    @Override
    public TurnEvents getOwnerTurnEvents() {
        return ownerTurnEvents;
    }

}
