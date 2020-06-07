package it.polimi.ingsw.server.model.turnstates;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Turn;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class WinTest {
    Turn mockTurn = mock(Turn.class);
    Game mockGame = mock(Game.class);
    Win winTest = new Win();

    @Test
    void setup() {
        when(mockTurn.getGame()).thenReturn(mockGame);
        this.winTest.setup(this.mockTurn);
        verify(this.mockTurn).setNextState(TurnState.WIN.getTurnState());
        verify(this.mockGame).triggerWinningTurn();
    }
}