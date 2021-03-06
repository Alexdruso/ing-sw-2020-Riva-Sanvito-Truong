package it.polimi.ingsw.server.model.turnstates;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Turn;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class EndTest {
    Game mockGame = mock(Game.class);
    Turn mockTurn = mock(Turn.class);
    End endTest = new End();

    @Test
    void setup() {
        when(mockTurn.getGame()).thenReturn(mockGame);
        this.endTest.setup(this.mockTurn);
        verify(this.mockTurn).setNextState(TurnState.END.getTurnState());
        verify(mockGame).triggerEndTurn();
    }
}