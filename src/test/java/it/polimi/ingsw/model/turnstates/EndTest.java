package it.polimi.ingsw.model.turnstates;

import it.polimi.ingsw.model.Turn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class EndTest {
    Turn mockTurn = mock(Turn.class);
    End endTest = new End();

    @Test
    void setup() {
        this.endTest.setup(this.mockTurn);
        verify(this.mockTurn).setNextState(TurnState.END.getTurnState());
    }

    @Test
    void canEndTurn() {
        assertTrue(this.endTest.canEndTurn(this.mockTurn));
    }

    @Test
    void endTurn() {
        try{
            this.endTest.endTurn(this.mockTurn);
            assertTrue(true);
        }
        catch (Exception e){
            fail();
        }
    }
}