package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.turnevents.TurnEvents;

/**
 * The god card Hephaestus.
 */
class Hephaestus extends AbstractGod {
    /**
     * The TurnEvents for the owner of the Hephaestus god card.
     */
    private static final TurnEvents ownerTurnEvents = new TurnEvents() {
        @Override
        protected void onBeforeBuild() {
            //TODO
            /*
            try {
                List<BuildAction> lastBuildActions = turn.getBuildActions();
                if (lastBuildActions.size() == 1) {
                    turn.setAllowSkipBuild(true);
                    BuildAction lastBuild = (MoveAction) lastBuildActions.get(0);
                    turn.clearAllowedWorkers();
                    turn.addAllowedWorker(lastMove.getWorker());
                    worker.getBlockBuildableCells.removeTargets(TargetCells.allCells);
                    if (lastBuild.cell.canBuildBlock) {
                        worker.getBlockBuildableCells.addTargets(lastBuild.cell);
                    }
                    worker.getDomeBuildableCells.removeTargets(TargetCells.allCells);
                }
            }
            catch (ClassCastException e) {
                //TODO
            }
             */
        }

        @Override
        protected void onAfterBuild() {
            //TODO
            /*
            List<BuildAction> lastBuildActions = turn.getBuildActions();
            if (lastBuildActions.size() == 1) {
                turn.setNextState(TurnState.BEFORE_BUILD);
            }
             */
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
