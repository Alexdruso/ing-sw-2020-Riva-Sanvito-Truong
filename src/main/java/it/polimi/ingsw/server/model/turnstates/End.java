package it.polimi.ingsw.server.model.turnstates;


import it.polimi.ingsw.server.model.Turn;

/**
 * The end turn state.
 */
class End implements AbstractTurnState {


    /**
     * This method ends the turn
     *
     * @param turn the Context
     */
    public void setup(Turn turn) {
        //Sets default next state
        turn.setNextState(TurnState.END.getTurnState());
        turn.getGame().triggerEndTurn();
    }
}
