package it.polimi.ingsw.model.turnstates;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.actions.MoveAction;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.TargetCells;
import it.polimi.ingsw.model.turnevents.TurnEventsManager;
import it.polimi.ingsw.model.workers.Worker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MoveTest {
    Move testMove = new Move();
    Game mockGame = mock(Game.class);
    Turn mockTurn = mock(Turn.class);
    Worker mockWorker = mock(Worker.class);
    Cell spiedCell = spy(new Cell(0,0));
    TargetCells mockTargetCells = mock(TargetCells.class);
    Player mockPlayer = mock(Player.class);
    TurnEventsManager mockTurnEventsManager = mock(TurnEventsManager.class);

    @Test
    void setup() {
    }

    @Test
    void canMoveTo() {
        when(this.mockTurn.getWorkerWalkableCells(this.mockWorker)).thenReturn(this.mockTargetCells);
        when(this.mockTargetCells.getPosition(this.spiedCell.getX(),this.spiedCell.getY())).thenReturn(true).thenReturn(false);
        assertTrue(this.testMove.canMoveTo(this.mockWorker,this.spiedCell,this.mockTurn));
        assertFalse(this.testMove.canMoveTo(this.mockWorker,this.spiedCell,this.mockTurn));
        verify(this.mockTurn, times(2)).getWorkerWalkableCells(this.mockWorker);
        verify(this.mockTargetCells, times(2)).getPosition(this.spiedCell.getX(),this.spiedCell.getY());
    }

    @Test
    void moveTo() {
        when(this.mockTurn.getGame()).thenReturn(this.mockGame);
        when(this.mockWorker.getCell()).thenReturn(this.spiedCell);
        when(this.mockTurn.getPlayer()).thenReturn(this.mockPlayer);
        when(this.mockPlayer.getTurnEventsManager()).thenReturn(this.mockTurnEventsManager);
        this.testMove.moveTo(this.mockWorker, this.spiedCell, this.mockTurn);
        verify(this.mockTurn).addPerformedAction(any(MoveAction.class));
        verify(this.mockTurnEventsManager).processAfterMovementEvents(this.mockTurn);
        verify(this.mockGame).setWorkerCell(this.mockWorker, this.spiedCell);
    }
}