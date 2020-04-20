package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.actions.MoveAction;
import it.polimi.ingsw.model.board.TargetCells;
import it.polimi.ingsw.model.turnevents.TurnEvents;

import java.util.List;
import java.util.stream.Collectors;

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
