package it.polimi.ingsw.server.model.gods;

import it.polimi.ingsw.server.model.Turn;
import it.polimi.ingsw.server.model.turnevents.TurnEvents;
import it.polimi.ingsw.server.model.workers.Worker;

/**
 * The god card Atlas.
 */
class Atlas extends AbstractGod {
    /**
     * The TurnEvents for the owner of the Atlas god card.
     */
    private static final TurnEvents ownerTurnEvents = new TurnEvents() {
        @Override
        protected void onBeforeBuild(Turn turn) {
            Worker[] workers = turn.getPlayer().getWorkers();
            for (Worker worker : workers) {
                turn.getWorkerDomeBuildableCells(worker).union(turn.getWorkerBlockBuildableCells(worker));
            }
        }
    };

    /**
     * Gets the god's name.
     *
     * @return the name
     */
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
