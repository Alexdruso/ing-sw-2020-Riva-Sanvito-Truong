package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.workers.Worker;
import it.polimi.ingsw.model.actions.BuildAction;
import it.polimi.ingsw.model.board.Tower;
import it.polimi.ingsw.model.turnevents.TurnEvents;

import java.util.List;

/**
 * The god card Hephaestus.
 */
class Hephaestus extends AbstractGod {
    /**
     * The TurnEvents for the owner of the Hephaestus god card.
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
                turn.getWorkerBlockBuildableCells(lastBuildWorker).setAllTargets(false);
                Tower lastBuildTower = lastBuild.getTargetCell().getTower();
                //TODO: let's decide if this check is better suited here or in Tower (like Tower::isBlockBuildable())
                if (!lastBuildTower.isComplete() && lastBuildTower.getCurrentLevel() < 3) {
                    turn.getWorkerBlockBuildableCells(lastBuildWorker).setPosition(lastBuild.getTargetCell(), true);
                }
                turn.getWorkerDomeBuildableCells(lastBuildWorker).setAllTargets(false);
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
        return "Hephaestus";
    }

    /**
     * Gets the TurnEvents for the player owning Hephaestus.
     *
     * @return the TurnEvents for the player owning Hephaestus
     */
    @Override
    public TurnEvents getOwnerTurnEvents() {
        return ownerTurnEvents;
    }

}
