package it.polimi.ingsw.model.turnstates;

import it.polimi.ingsw.model.Turn;

class Start extends AbstractTurnState {

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
     * This method sets up the first actual state of the turn and performs
     * some default calculation on the buildableCells and walkableCells
     *
     * @param turn the Context
     */
    @Override
    public void startTurn(Turn turn) {
        super.startTurn(turn);
    }

}
