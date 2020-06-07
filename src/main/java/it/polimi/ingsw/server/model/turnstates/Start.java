package it.polimi.ingsw.server.model.turnstates;

import it.polimi.ingsw.server.model.Turn;

class Start implements AbstractTurnState {

    /**
     * This method sets things up before we can use the other methods provided by the state
     *
     * @param turn the Context
     */
    public void setup(Turn turn) {
        //Sets default next state
        turn.setNextState(TurnState.MOVE.getTurnState());
        turn.getPlayer().getTurnEventsManager().processTurnStartEvents(turn);
    }
}
