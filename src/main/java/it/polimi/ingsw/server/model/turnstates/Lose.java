package it.polimi.ingsw.server.model.turnstates;

import it.polimi.ingsw.server.model.Turn;

/**
 * The lose turn state.
 */
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
