package it.polimi.ingsw.model.turnstates;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

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