package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.turnevents.TurnEvents;

/**
 * The god card Atlas.
 */
class Atlas extends AbstractGod {
    /**
     * The TurnEvents for the owner of the Atlas god card.
     */
    class OwnerTurnEvents extends TurnEvents {
        @Override
        protected void onBeforeBuild() {
            //TODO
        }
    }

    @Override
    public String getName() {
        return "Atlas";
    }

    @Override
    public TurnEvents getOwnerTurnEvents() {
        return new OwnerTurnEvents();
    }

}
