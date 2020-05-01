package it.polimi.ingsw.model.turnstates;

import it.polimi.ingsw.model.Turn;

class Win implements AbstractTurnState {
    /**
     * This method sets things up before we can use the other methods provided by the state
     *
     * @param turn the Context
     */
    public void setup(Turn turn) {
        //Sets default next state
        turn.setNextState(TurnState.WIN.getTurnState());
        //TODO
    }
}
