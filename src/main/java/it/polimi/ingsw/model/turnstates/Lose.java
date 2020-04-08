package it.polimi.ingsw.model.turnstates;

import it.polimi.ingsw.model.Turn;

class Lose extends AbstractTurnState{
    /**
     * This method sets things up before we can use the other methods provided by the state
     *
     * @param turn the Context
     */
    @Override
    public void setup(Turn turn) {
        //Sets default next state
        turn.setNextState(TurnState.LOSE.getTurnState());
        //TODO
    }
}
