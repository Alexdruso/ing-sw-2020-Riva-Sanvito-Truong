package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.turnevents.TurnEvents;

/**
 * The god card Atlas.
 */
class Atlas extends AbstractGod {
    /**
     * The TurnEvents for the owner of the Atlas god card.
     */
    private static final TurnEvents ownerTurnEvents = new TurnEvents() {
        @Override
        protected void onBeforeBuild() {
            //TODO
            /*
            worker.getDomeBuildableCells().addTargets(worker.getBlockBuildableCells());
             */
        }
    };

    @Override
    public String getName() {
        return "Atlas";
    }

    /**
     * Gets the TurnEvents for the player owning Atlas.
     *
     * @return the TurnEvents for the player owning Atlas
     */
    @Override
    public TurnEvents getOwnerTurnEvents() {
        return ownerTurnEvents;
    }

}
