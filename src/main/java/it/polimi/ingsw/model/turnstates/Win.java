package it.polimi.ingsw.model.turnstates;

import it.polimi.ingsw.model.Turn;

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
