package it.polimi.ingsw.model.turnstates;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.actions.BuildAction;
import it.polimi.ingsw.model.actions.MoveAction;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.TargetCells;
import it.polimi.ingsw.model.turnevents.TurnEventsManager;
import it.polimi.ingsw.model.workers.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class BuildTest {
    Build testBuild;
    Game mockGame;
    Turn mockTurn;
    Worker mockWorker;
    Cell spiedCell ;
    TargetCells mockTargetCells;
    Player mockPlayer;
    TurnEventsManager mockTurnEventsManager;

    @BeforeEach
    void reset(){
        this.testBuild = new Build();
        this.mockGame = mock(Game.class);
        this.mockTurn = mock(Turn.class);
        this.mockWorker = mock(Worker.class);
        this.spiedCell = spy(new Cell(0,0));
        this.mockTargetCells = mock(TargetCells.class);
        this.mockPlayer = mock(Player.class);
        this.mockTurnEventsManager = mock(TurnEventsManager.class);
    }
    @Test
    void setup() {
    }

    @Test
    void canBuildDomeIn() {
        when(mockTurn.getWorkerDomeBuildableCells(mockWorker)).thenReturn(mockTargetCells);
        when(mockTargetCells.getPosition(spiedCell.getX(), spiedCell.getY())).thenReturn(true).thenReturn(false);
        assertTrue(this.testBuild.canBuildDomeIn(this.mockWorker,this.spiedCell,this.mockTurn));
        assertFalse(this.testBuild.canBuildDomeIn(this.mockWorker,this.spiedCell,this.mockTurn));
        verify(this.mockTurn, times(2)).getWorkerDomeBuildableCells(this.mockWorker);
        verify(this.mockTargetCells, times(2)).getPosition(spiedCell.getX(), spiedCell.getY());
    }

    @Test
    void buildDomeIn() {
        when(this.mockTurn.getGame()).thenReturn(this.mockGame);
        when(this.mockWorker.getCell()).thenReturn(this.spiedCell);
        when(this.mockTurn.getPlayer()).thenReturn(this.mockPlayer);
        when(this.mockPlayer.getTurnEventsManager()).thenReturn(this.mockTurnEventsManager);
        this.testBuild.buildDomeIn(this.mockWorker, this.spiedCell, this.mockTurn);
        verify(this.mockTurn).addPerformedAction(any(BuildAction.class));
        verify(this.mockTurnEventsManager).processAfterBuildEvents(this.mockTurn);
        assertEquals(0, this.spiedCell.getTower().getCurrentLevel());
        assertTrue(this.spiedCell.getTower().isComplete());
    }

    @Test
    void canBuildBlockIn() {
        when(mockTurn.getWorkerBlockBuildableCells(mockWorker)).thenReturn(mockTargetCells);
        when(mockTargetCells.getPosition(spiedCell.getX(), spiedCell.getY())).thenReturn(true).thenReturn(false);
        assertTrue(this.testBuild.canBuildBlockIn(this.mockWorker,this.spiedCell,this.mockTurn));
        assertFalse(this.testBuild.canBuildBlockIn(this.mockWorker,this.spiedCell,this.mockTurn));
        verify(this.mockTurn, times(2)).getWorkerBlockBuildableCells(this.mockWorker);
        verify(this.mockTargetCells, times(2)).getPosition(spiedCell.getX(), spiedCell.getY());
    }

    @Test
    void buildBlockIn() {
        when(this.mockTurn.getGame()).thenReturn(this.mockGame);
        when(this.mockWorker.getCell()).thenReturn(this.spiedCell);
        when(this.mockTurn.getPlayer()).thenReturn(this.mockPlayer);
        when(this.mockPlayer.getTurnEventsManager()).thenReturn(this.mockTurnEventsManager);
        this.testBuild.buildBlockIn(this.mockWorker, this.spiedCell, this.mockTurn);
        verify(this.mockTurn).addPerformedAction(any(BuildAction.class));
        verify(this.mockTurnEventsManager).processAfterBuildEvents(this.mockTurn);
        assertEquals(1, this.spiedCell.getTower().getCurrentLevel());
        assertFalse(this.spiedCell.getTower().isComplete());
    }
}