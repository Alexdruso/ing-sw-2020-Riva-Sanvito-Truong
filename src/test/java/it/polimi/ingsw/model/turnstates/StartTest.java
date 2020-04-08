package it.polimi.ingsw.model.turnstates;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.turnevents.TurnEventsManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
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