package it.polimi.ingsw.server.model.gods;

import it.polimi.ingsw.server.model.Turn;
import it.polimi.ingsw.server.model.actions.BuildAction;
import it.polimi.ingsw.server.model.actions.MoveAction;
import it.polimi.ingsw.server.model.board.TargetCells;
import it.polimi.ingsw.server.model.turnevents.TurnEvents;
import it.polimi.ingsw.server.model.turnstates.TurnState;
import it.polimi.ingsw.server.model.workers.Worker;

import java.util.List;

/**
 * The god card Prometheus.
 */
class Prometheus extends AbstractGod {
    /**
     * The TurnEvents for the owner of the Prometheus god card.
     */
    private static final TurnEvents ownerTurnEvents = new TurnEvents() {
        @Override
        protected void onTurnStart(Turn turn) {
            turn.setNextState(TurnState.BUILD.getTurnState());
        }

        @Override
        protected void onBeforeMovement(Turn turn) {
            List<BuildAction> lastBuildActions = turn.getBuilds();
            if (!lastBuildActions.isEmpty()) {
                BuildAction lastBuild = lastBuildActions.get(0);
                Worker lastBuildWorker = lastBuild.getWorker();
                turn.clearAllowedWorkers();
                turn.addAllowedWorker(lastBuildWorker);
                TargetCells notHigherCells = new TargetCells();
                notHigherCells.setAllTargets(false);
                turn.getGame().getBoard().getCellsList().stream()
                        .filter(
                                cell -> cell.getTower().getCurrentLevel() <= lastBuildWorker.getCell().getTower().getCurrentLevel()
                        )
                        .forEach(
                                cell -> notHigherCells.setPosition(cell, true)
                        );
                turn.getWorkerWalkableCells(lastBuildWorker).intersect(notHigherCells);
            }
        }

        @Override
        protected void onBeforeBuild(Turn turn) {
            List<MoveAction> moveActions = turn.getMoves();
            if (moveActions.isEmpty()) {
                turn.setSkippable(true);
                turn.setNextState(TurnState.MOVE.getTurnState());
            }
        }
    };

    @Override
    public String getName() {
        return "Prometheus";
    }

    /**
     * Gets the TurnEvents for the player owning Prometheus.
     *
     * @return the TurnEvents for the player owning Prometheus
     */
    @Override
    public TurnEvents getOwnerTurnEvents() {
        return ownerTurnEvents;
    }

}
