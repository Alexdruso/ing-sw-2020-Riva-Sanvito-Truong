package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.actions.MoveAction;
import it.polimi.ingsw.model.turnevents.TurnEvents;
import it.polimi.ingsw.model.turnstates.TurnState;
import it.polimi.ingsw.model.workers.Worker;

import java.util.List;

/**
 * The god card Artemis.
 */
class Artemis extends AbstractGod {
    /**
     * The TurnEvents for the owner of the Artemis god card.
     */
    private static final TurnEvents ownerTurnEvents = new TurnEvents(){
        @Override
        protected void onBeforeMovement(Turn turn) {
            List<MoveAction> moveActions = turn.getMoves();
            if (moveActions.size() == 1) {
                turn.setSkippable(true);
                MoveAction lastMove = (MoveAction) moveActions.get(0);
                Worker lastMoveWorker = lastMove.getWorker();
                turn.clearAllowedWorkers();
                turn.addAllowedWorker(lastMoveWorker);
                turn.getWorkerWalkableCells(lastMoveWorker).setPosition(lastMove.getSourceCell(), false);
            }
        }

        @Override
        protected void onAfterMovement(Turn turn) {
            List<MoveAction> moveActions = turn.getMoves();
            if (moveActions.size() == 1) {
                turn.setNextState(TurnState.MOVE.getTurnState());
            }
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
