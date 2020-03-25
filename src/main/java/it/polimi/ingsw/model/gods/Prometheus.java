package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.actions.BuildAction;
import it.polimi.ingsw.model.actions.MoveAction;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.TargetCells;
import it.polimi.ingsw.model.turnevents.TurnEvents;

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
            //TODO
            /*
            turn.setAllowSkipBuild(true);
            turn.setNextState(TurnState.BEFORE_BUILD);
             */
        }

        @Override
        protected void onBeforeMovement(Turn turn) {
            //TODO
//            turn.setAllowSkipBuild(false);
            List<BuildAction> lastBuildActions = turn.getBuilds();
            if (lastBuildActions.size() > 0) {
                BuildAction lastBuild = lastBuildActions.get(0);
                Worker lastBuildWorker = lastBuild.getPerformer();
//                TODO
//                turn.clearAllowedWorkers();
//                turn.addAllowedWorker(lastBuild.worker);
                TargetCells notHigherCells = new TargetCells();
                notHigherCells.setAllTargets(false);
//                TODO
//                turn.getGame().getBoard().getCellsList().stream()
//                        .filter(
//                                cell -> cell.getTower().getCurrentLevel() <= lastBuildWorker.getCell().getTower().getCurrentLevel()
//                        )
//                        .forEach(
//                                cell -> notHigherCells.setPosition(cell, true)
//                        );
                turn.getWorkerWalkableCells(lastBuildWorker).intersect(notHigherCells);
            }
        }

        @Override
        protected void onAfterBuild(Turn turn) {
            List<MoveAction> lastMoveActions = turn.getMoves();
            if (lastMoveActions.size() == 0) {
//                TODO
//                turn.setNextState(TurnState.BEFORE_MOVE);
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
