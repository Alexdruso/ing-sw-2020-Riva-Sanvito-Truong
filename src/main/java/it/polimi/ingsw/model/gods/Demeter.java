package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.turnstates.TurnState;
import it.polimi.ingsw.model.workers.Worker;
import it.polimi.ingsw.model.actions.BuildAction;
import it.polimi.ingsw.model.turnevents.TurnEvents;

import java.util.List;

/**
 * The god card Demeter.
 */
class Demeter extends AbstractGod {
    /**
     * The TurnEvents for the owner of the Demeter god card.
     */
    private static final TurnEvents ownerTurnEvents = new TurnEvents() {
        @Override
        protected void onBeforeBuild(Turn turn) {
            List<BuildAction> lastBuildActions = turn.getBuilds();
            if (lastBuildActions.size() == 1) {
                turn.setSkippable(true);
                BuildAction lastBuild = lastBuildActions.get(0);
                Worker lastBuildWorker = lastBuild.getPerformer();

//                TODO
//                turn.clearAllowedWorkers();
//                turn.addAllowedWorker(lastMove.getWorker());
                turn.getWorkerBlockBuildableCells(lastBuildWorker).setPosition(lastBuild.getTargetCell(), false);
                turn.getWorkerDomeBuildableCells(lastBuildWorker).setPosition(lastBuild.getTargetCell(), false);

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
        return "Demeter";
    }

    /**
     * Gets the TurnEvents for the player owning Demeter.
     *
     * @return the TurnEvents for the player owning Demeter
     */
    @Override
    public TurnEvents getOwnerTurnEvents() {
        return ownerTurnEvents;
    }

}
