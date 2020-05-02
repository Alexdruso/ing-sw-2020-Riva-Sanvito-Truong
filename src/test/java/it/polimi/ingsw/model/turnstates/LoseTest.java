package it.polimi.ingsw.model.turnstates;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Turn;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class LoseTest {
    Turn mockTurn = mock(Turn.class);
    Game mockGame = mock(Game.class);
    Lose loseTest = new Lose();

    @Test
    void setup() {
        when(mockTurn.getGame()).thenReturn(mockGame);
        loseTest.setup(this.mockTurn);
        verify(this.mockTurn).setNextState(TurnState.LOSE.getTurnState());
        verify(this.mockGame).triggerLosingTurn();
    }
}