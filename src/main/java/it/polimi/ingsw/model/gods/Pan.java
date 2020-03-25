package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.actions.MoveAction;
import it.polimi.ingsw.model.turnevents.TurnEvents;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The god card Pan.
 */
class Pan extends AbstractGod {
    /**
     * The TurnEvents for the owner of the Pan god card.
     */
    private static final TurnEvents ownerTurnEvents = new TurnEvents() {
        @Override
        protected void computeWinCondition(Turn turn) {
            List<MoveAction> moveDownActions = turn.getMoves().stream().filter(action -> action.getTargetLevel() - action.getSourceLevel() <= -2).collect(Collectors.toList());
            if (moveDownActions.size() > 0) {
//                TODO
//                turn.setNextState(TurnState.WIN);
            }
        }
    };

    @Override
    public String getName() {
        return "Pan";
    }

    /**
     * Gets the TurnEvents for the player owning Pan.
     *
     * @return the TurnEvents for the player owning Pan
     */
    @Override
    public TurnEvents getOwnerTurnEvents() {
        return ownerTurnEvents;
    }

}
