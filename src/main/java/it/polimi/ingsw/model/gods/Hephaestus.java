package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.turnstates.TurnState;
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
                turn.setSkippable(true);
                BuildAction lastBuild = lastBuildActions.get(0);
                Worker lastBuildWorker = lastBuild.getPerformer();

                turn.clearAllowedWorkers();
                turn.addAllowedWorker(lastBuildWorker);

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
                turn.setNextState(TurnState.BUILD.getTurnState());
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
