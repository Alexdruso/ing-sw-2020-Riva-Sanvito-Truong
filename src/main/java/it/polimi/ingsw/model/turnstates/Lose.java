package it.polimi.ingsw.model.turnstates;

import it.polimi.ingsw.model.Turn;

class Lose implements AbstractTurnState {
    /**
     * This method triggers a losing turn
     *
     * @param turn the Context
     */
    public void setup(Turn turn) {
        //Sets default next state
        turn.setNextState(TurnState.LOSE.getTurnState());
        turn.getGame().triggerLosingTurn();
    }
}
