package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.turnevents.TurnEvents;

/**
 * The god card Athena.
 */
class Athena extends AbstractGod {
    /**
     * The TurnEvents for the opponents of the owner of the Athena god card.
     */
    private static final TurnEvents opponentsTurnEvents = new TurnEvents() {
        @Override
        protected void onBeforeMovement(Turn turn) {
            //TODO
            /*
            Player athenaPlayer = game.getPlayerByGod();
            game.getTurnByPlayer(athenaPlayer).ifPresent(athenaLastTurn -> {
                List<Action> athenaMoveUpActions = athenaLastTurn.getActions(action -> {
                    if (!(action instanceof MoveAction)) return false;
                    MoveAction moveAction = (MoveAction) action;
                    return action.targetLevel > action.sourceLevel;
                });
                if (athenaMoveUpActions.size() > 0) {
                    TargetCells higherCells = TargetCells.fromMinimumHeight(worker.getCell().getHeight() + 1);
                    worker.getWalkableCells().removeTargets(higherCells);
                }
            });
             */
        }
    };

    @Override
    public String getName() {
        return "Athena";
    }

    /**
     * Gets the TurnEvents for the opponents of the player owning Athena.
     *
     * @return the TurnEvents for the opponents of the player owning Athena
     */
    @Override
    public TurnEvents getOpponentsTurnEvents() {
        return opponentsTurnEvents;
    }

}
