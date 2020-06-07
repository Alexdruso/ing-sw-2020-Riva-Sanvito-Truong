package it.polimi.ingsw.server.model.gods;

import it.polimi.ingsw.server.model.Turn;
import it.polimi.ingsw.server.model.actions.MoveAction;
import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Cell;
import it.polimi.ingsw.server.model.turnevents.TurnEvents;
import it.polimi.ingsw.server.model.turnstates.TurnState;

import java.util.List;

/**
 * The god card Triton.
 */
public class Triton extends AbstractGod {
    /**
     * The TurnEvents for the owner of the Triton god card.
     */
    private static final TurnEvents ownerTurnEvents = new TurnEvents() {
        @Override
        protected void onBeforeMovement(Turn turn) {
            List<MoveAction> moveActions = turn.getMoves();
            if (!moveActions.isEmpty()) {
                MoveAction lastMove = moveActions.get(moveActions.size() - 1);
                Cell targetCell = lastMove.getTargetCell();
                Board board = turn.getGame().getBoard();
                //if last move was on border, then we can skip this move
                if (
                        targetCell.getX() == 0
                                || targetCell.getX() == board.getDimension() - 1
                                || targetCell.getY() == 0
                                || targetCell.getY() == board.getDimension() - 1
                ) {
                    turn.setSkippable(true);
                }
            }
        }

        @Override
        protected void onAfterMovement(Turn turn) {
            List<MoveAction> moveActions = turn.getMoves();
            MoveAction lastMove = moveActions.get(moveActions.size() - 1);
            Cell targetCell = lastMove.getTargetCell();
            Board board = turn.getGame().getBoard();
            //if last move was on border, then we can move again
            if (
                    targetCell.getX() == 0
                            || targetCell.getX() == board.getDimension() - 1
                            || targetCell.getY() == 0
                            || targetCell.getY() == board.getDimension() - 1
            ) {
                turn.setNextState(TurnState.MOVE.getTurnState());
            }
        }
    };

    /**
     * Gets the god's name.
     *
     * @return the name
     */
    @Override
    public String getName() {
        return "Triton";
    }

    /**
     * Gets the TurnEvents for the player owning Triton.
     *
     * @return the TurnEvents for the player owning Triton
     */
    @Override
    public TurnEvents getOwnerTurnEvents() {
        return ownerTurnEvents;
    }
}
