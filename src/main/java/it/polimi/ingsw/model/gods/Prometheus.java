package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.turnevents.TurnEvents;

/**
 * The god card Prometheus.
 */
class Prometheus extends AbstractGod {
    /**
     * The TurnEvents for the owner of the Prometheus god card.
     */
    private static final TurnEvents ownerTurnEvents = new TurnEvents() {
        @Override
        protected void onTurnStart() {
            //TODO
            /*
            turn.setAllowSkipBuild(true);
            turn.setNextState(TurnState.BEFORE_BUILD);
             */
        }

        @Override
        protected void onBeforeMovement() {
            //TODO
            /*
           turn.setAllowSkipBuild(false);
            List<BuildAction> lastBuildActions = turn.getBuildActions();
            if (lastBuildActions.size() > 0) {
                BuildAction lastBuild = (BuildAction) lastBuildActions.get(0);
                turn.clearAllowedWorkers();
                turn.addAllowedWorker(lastBuild.worker);
                worker.getWalkableCells.removeTargets(lastMove.sourceCell);
                TargetCells higherCells = TargetCells.fromMinimumHeight(worker.getCell().getHeight());
                worker.getWalkableCells().removeTargets(higherCells);
            }
             */
        }

        @Override
        protected void onAfterBuild() {
            //TODO
            /*
            List<MoveAction> lastMoveActions = turn.getMoveActions();
            if (lastMoveActions.size() == 0) {
                turn.setNextState(TurnState.BEFORE_MOVE);
            }
             */
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
