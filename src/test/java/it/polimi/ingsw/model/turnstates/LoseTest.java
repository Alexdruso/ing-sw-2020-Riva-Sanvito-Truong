package it.polimi.ingsw.model.turnstates;

import it.polimi.ingsw.model.Turn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LoseTest {
    Turn mockTurn = mock(Turn.class);
    Lose loseTest = new Lose();

    @Test
    void setup() {
        loseTest.setup(this.mockTurn);
        verify(this.mockTurn).setNextState(TurnState.LOSE.getTurnState());
    }
}