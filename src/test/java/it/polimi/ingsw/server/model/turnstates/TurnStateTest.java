package it.polimi.ingsw.server.model.turnstates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TurnStateTest {

    //Checks if TurnState returns right State
    @Test
    void rightReturnState() {
        assertEquals(TurnState.START.getTurnState().getClass(), Start.class);
        assertEquals(TurnState.MOVE.getTurnState().getClass(), Move.class);
        assertEquals(TurnState.BUILD.getTurnState().getClass(), Build.class);
        assertEquals(TurnState.END.getTurnState().getClass(), End.class);
        assertEquals(TurnState.LOSE.getTurnState().getClass(), Lose.class);
        assertEquals(TurnState.WIN.getTurnState().getClass(), Win.class);
    }

    //Check if TurnState makes a singleton pattern
    @Test
    void isSingleton(){
        assertEquals(TurnState.START.getTurnState(), TurnState.START.getTurnState());
        assertEquals(TurnState.MOVE.getTurnState(), TurnState.MOVE.getTurnState());
        assertEquals(TurnState.BUILD.getTurnState(), TurnState.BUILD.getTurnState());
        assertEquals(TurnState.END.getTurnState(), TurnState.END.getTurnState());
        assertEquals(TurnState.LOSE.getTurnState(), TurnState.LOSE.getTurnState());
        assertEquals(TurnState.WIN.getTurnState(), TurnState.WIN.getTurnState());
    }
}