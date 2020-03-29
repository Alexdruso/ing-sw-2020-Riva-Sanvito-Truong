package it.polimi.ingsw.model.turnstates;


import it.polimi.ingsw.model.Turn;

class End extends AbstractTurnState {


    /**
     * This method sets things up before we can use the other methods provided by the state
     *
     * @param turn the Context
     */
    @Override
    public void setup(Turn turn) {
        //Sets default next state
        turn.setNextState(TurnState.END.getTurnState());
        //TODO add default behavior
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
     */
    @Override
    public void endTurn(Turn turn) {
        turn.getPlayer().getTurnEventsManager().processTurnEndEvents(turn);
        //TODO think about a better way
        if(turn.getMoves().size() == 0 || turn.getBuilds().size() == 0) turn.setLosingTurn();
        if (!turn.isLosingTurn()) turn.getPlayer().getTurnEventsManager().processWinConditionEvents(turn);
    }
}
