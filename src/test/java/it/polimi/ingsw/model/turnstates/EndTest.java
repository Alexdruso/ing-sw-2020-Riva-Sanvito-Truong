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
}