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

    @BeforeAll
    static void setMock(){
        when(mockPlayer.getTurnEventsManager()).thenReturn(mockTurnEventsManager);
        when(mockTurn.getPlayer()).thenReturn(mockPlayer);
    }

    @Test
    void setup() {
        this.testStart.setup(mockTurn);
        verify(mockTurn).setNextState(TurnState.MOVE.getTurnState());
    }

    @Test
    void startTurn() {
        this.testStart.startTurn(mockTurn);
        verify(mockTurnEventsManager).processTurnStartEvents(mockTurn);
    }
}