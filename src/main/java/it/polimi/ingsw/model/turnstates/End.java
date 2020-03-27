package it.polimi.ingsw.model.turnstates;


import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.workers.Worker;

public class End extends TurnState {


    /**
     * This method sets things up before we can use the other methods provided by the state
     *
     * @param turn the Context
     */
    @Override
    public void setup(Turn turn) {
        super.setup(turn);
    }

    /**
     * This method checks if we can end the turn
     *
     * @param turn the Context
     * @return if the player can end the turn
     */
    @Override
    public boolean canEndTurn(Turn turn) {
        return super.canEndTurn(turn);
    }

    /**
     * This method ends the turn
     *
     * @param turn the Context
     */
    @Override
    public void endTurn(Turn turn) {
        super.endTurn(turn);
    }
}
