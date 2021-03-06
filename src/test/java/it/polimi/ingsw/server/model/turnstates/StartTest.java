package it.polimi.ingsw.server.model.turnstates;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Turn;
import it.polimi.ingsw.server.model.turnevents.TurnEventsManager;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class StartTest {
    static Turn mockTurn = mock(Turn.class);
    static Player mockPlayer = mock(Player.class);
    static TurnEventsManager mockTurnEventsManager = mock(TurnEventsManager.class);
    Start testStart = new Start();

    @Test
    void setup() {
        when(mockPlayer.getTurnEventsManager()).thenReturn(mockTurnEventsManager);
        when(mockTurn.getPlayer()).thenReturn(mockPlayer);
        this.testStart.setup(mockTurn);
        verify(mockTurn).setNextState(TurnState.MOVE.getTurnState());
        verify(mockTurnEventsManager).processTurnStartEvents(mockTurn);
    }
}