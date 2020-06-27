package it.polimi.ingsw.server.model.gods;

import it.polimi.ingsw.server.model.Turn;
import it.polimi.ingsw.server.model.actions.MoveAction;
import it.polimi.ingsw.server.model.board.TargetCells;
import it.polimi.ingsw.server.model.turnevents.TurnEvents;

import java.util.List;

/**
 * The god card Hera.
 */
class Hera extends AbstractGod {
    /**
     * The TurnEvents for the opponents of the owner of the Hera god card.
     */
    private static final TurnEvents opponentsTurnEvents = new TurnEvents() {
        @Override
        protected void computeWinCondition(Turn turn) {
            if (!turn.isWinningTurn()) {
                return;
            }

            List<MoveAction> moves = turn.getMoves();
            if(moves.isEmpty()) {
                return;
            }

            MoveAction lastMove = moves.get(moves.size()-1);
            if(lastMove.getTargetLevel() == 3 && lastMove.getSourceLevel() < 3){
                // This would normally trigger a winning condition; let's check if we can allow it.
                TargetCells boardPerimeter = new TargetCells();
                boardPerimeter.setEdges(true);

                if (boardPerimeter.getPosition(lastMove.getTargetCell())) {
                    // Last move was on perimeter: Hera anti-win condition triggers
                    turn.setNeutralTurn();
                }
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
        return "Hera";
    }

    /**
     * Gets the TurnEvents for the opponents of the player owning Hera.
     *
     * @return the TurnEvents for the opponents of the player owning Hera
     */
    @Override
    public TurnEvents getOpponentsTurnEvents() {
        return opponentsTurnEvents;
    }

}
