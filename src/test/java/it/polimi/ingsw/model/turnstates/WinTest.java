package it.polimi.ingsw.model.turnstates;

import it.polimi.ingsw.model.Turn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class WinTest {
    Turn mockTurn = mock(Turn.class);
    Win winTest = new Win();

    @Test
    void setup() {
        this.winTest.setup(this.mockTurn);
        verify(this.mockTurn).setNextState(TurnState.WIN.getTurnState());
    }

    @Test
    void canEndTurn() {
        assertTrue(winTest.canEndTurn(this.mockTurn));
    }

    @Test
    void endTurn() {
        try{
            this.winTest.endTurn(this.mockTurn);
            assertTrue(true);
        }
        catch (Exception e){
            fail();
        }
    }
}