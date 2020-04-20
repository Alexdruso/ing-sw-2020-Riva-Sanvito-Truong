package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.actions.BuildAction;
import it.polimi.ingsw.model.board.TargetCells;
import it.polimi.ingsw.model.turnevents.TurnEvents;
import it.polimi.ingsw.model.turnstates.TurnState;
import it.polimi.ingsw.model.workers.Worker;

import java.util.List;

/**
 * The god card Hestia.
 */
class Hestia extends AbstractGod {
    /**
     * The TurnEvents for the owner of the Hestia god card.
     */
    private static final TurnEvents ownerTurnEvents = new TurnEvents() {
        @Override
        protected void onBeforeBuild(Turn turn) {
            List<BuildAction> lastBuildActions = turn.getBuilds();
            if (lastBuildActions.size() == 1) {
                turn.setSkippable(true);
                BuildAction lastBuild = lastBuildActions.get(0);
                Worker lastBuildWorker = lastBuild.getPerformer();

                turn.clearAllowedWorkers();
                turn.addAllowedWorker(lastBuildWorker);

                TargetCells nonPerimeterCells = new TargetCells();
                nonPerimeterCells.setEdges(true);
                nonPerimeterCells.invert();

                turn.getWorkerBlockBuildableCells(lastBuildWorker).intersect(nonPerimeterCells);

                turn.getWorkerDomeBuildableCells(lastBuildWorker).intersect(nonPerimeterCells);
            }
        }

        @Override
        protected void onAfterBuild(Turn turn) {
            List<BuildAction> lastBuildActions = turn.getBuilds();
            if (lastBuildActions.size() == 1) {
                turn.setNextState(TurnState.BUILD.getTurnState());
            }
        }
    };

    @Override
    public String getName() {
        return "Hestia";
    }

    /**
     * Gets the TurnEvents for the player owning Hestia.
     *
     * @return the TurnEvents for the player owning Hestia
     */
    @Override
    public TurnEvents getOwnerTurnEvents() {
        return ownerTurnEvents;
    }

}
