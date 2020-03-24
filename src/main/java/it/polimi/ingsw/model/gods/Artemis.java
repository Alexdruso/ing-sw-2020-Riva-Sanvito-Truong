package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.turnevents.TurnEvents;

/**
 * The god card Artemis.
 */
class Artemis extends AbstractGod {
    /**
     * The TurnEvents for the owner of the Artemis god card.
     */
    private static final TurnEvents ownerTurnEvents = new TurnEvents(){
        @Override
        protected void onTurnStart() { // diventa onBeforeMovement
            //TODO
            /*
            try {
                List<Action> lastMoveActions = turn.getActions(action -> action instanceof MoveAction);
                if (lastMoveActions.size() == 1) {
                    turn.setAllowSkipMove(true);
                    MoveAction lastMove = (MoveAction) lastMoveActions.get(0);
                    turn.clearAllowedWorkers();
                    turn.addAllowedWorker(lastMove.getWorker());
                    worker.getWalkableCells.removeTargets(lastMove.sourceCell);
                }
            }
            catch (ClassCastException e) {
                //TODO
            }
             */
        }

        @Override
        protected void onAfterMovement() {
            //TODO
            /*
            List<Action> lastActions = turn.getActions(action -> action instanceof MoveAction);
            if (lastActions.size() == 1) {
                turn.setNextState(TurnState.BEFORE_MOVE);
            }
             */
        }
    };

    @Override
    public String getName() {
        return "Artemis";
    }

    /**
     * Gets the TurnEvents for the player owning Artemis.
     *
     * @return the TurnEvents for the player owning Artemis
     */
    @Override
    public TurnEvents getOwnerTurnEvents() {
        return ownerTurnEvents;
    }

}
