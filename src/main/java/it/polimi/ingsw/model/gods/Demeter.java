package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.Worker;
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
//                TODO
//                turn.setAllowSkipBuild(true);
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
//                TODO
//                turn.setNextState(TurnState.BEFORE_BUILD);
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
