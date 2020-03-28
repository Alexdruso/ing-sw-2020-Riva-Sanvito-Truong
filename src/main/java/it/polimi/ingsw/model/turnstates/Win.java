package it.polimi.ingsw.model.turnstates;

import it.polimi.ingsw.model.Turn;

class Win extends AbstractTurnState{
    /**
     * This method sets things up before we can use the other methods provided by the state
     *
     * @param turn the Context
     */
    @Override
    public void setup(Turn turn) {
        //TODO
    }

    /**
     * This method checks if we can end the turn
     *
     * @param turn the Context
     * @return if the player can end the turn
     */
    @Override
    public boolean canEndTurn(Turn turn) {
        return true;
    }

    /**
     * This method ends the turn
     *
     * @param turn the Context
     * @throws InvalidTurnStateException if not in the right state
     */
    @Override
    public void endTurn(Turn turn) throws InvalidTurnStateException {
        //TODO
    }
}
