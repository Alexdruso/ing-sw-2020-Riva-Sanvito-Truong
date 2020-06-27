package it.polimi.ingsw.server.model.turnstates;

import it.polimi.ingsw.server.model.Turn;

/**
 * The win turn state.
 */
class Win implements AbstractTurnState {
    /**
     * This method triggers a winning turn
     *
     * @param turn the Context
     */
    public void setup(Turn turn) {
        //Sets default next state
        turn.setNextState(TurnState.WIN.getTurnState());
        turn.getGame().triggerWinningTurn();
    }
}
