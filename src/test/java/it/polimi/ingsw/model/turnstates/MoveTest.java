package it.polimi.ingsw.model.turnstates;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.actions.Action;
import it.polimi.ingsw.model.actions.MoveAction;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Component;
import it.polimi.ingsw.model.board.TargetCells;
import it.polimi.ingsw.model.turnevents.TurnEventsManager;
import it.polimi.ingsw.model.workers.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MoveTest {
    Move testMove = new Move();
    Game mockGame;
    Turn mockTurn;
    Worker mockWorker;
    Cell spiedCell;
    TargetCells mockTargetCells;
    Player mockPlayer;
    TurnEventsManager mockTurnEventsManager;

    @BeforeEach
    void reset() {
        this.mockGame = mock(Game.class);
        this.mockTurn = mock(Turn.class);
        this.mockWorker = mock(Worker.class);
        this.spiedCell = spy(new Cell(0, 0));
        this.mockTargetCells = mock(TargetCells.class);
        this.mockPlayer = mock(Player.class);
        this.mockTurnEventsManager = mock(TurnEventsManager.class);
    }

    @Test
    void setupWithAllWorkerAllowed() {
        //2 workers scenario w/ faked loss
        Worker mockWorker2 = mock(Worker.class);
        Board spiedBoard = spy(new Board());
        //worker 1 in cell 1 1
        spiedBoard.getCell(1, 1).setWorker(this.mockWorker);
        Cell cell1 = spiedBoard.getCell(1, 1);
        when(this.mockWorker.getCell()).thenReturn(cell1);
        //worker 2 in cell 3 3
        spiedBoard.getCell(3, 3).setWorker(mockWorker2);
        Cell cell2 = spiedBoard.getCell(3, 3);
        when(mockWorker2.getCell()).thenReturn(cell2);
        //the turn methods
        when(this.mockTurn.getPlayer()).thenReturn(this.mockPlayer);
        when(this.mockTurn.getGame()).thenReturn(this.mockGame);
        //non skippable turn
        when(this.mockTurn.isSkippable()).thenReturn(false);
        when(this.mockTurn.getPerformedAction()).thenReturn(new LinkedList<Action>());
        when(this.mockTurn.getAllowedWorkers()).thenReturn(new HashSet<Worker>(Arrays.asList(this.mockWorker, mockWorker2)));
        when(this.mockTurn.getWorkerWalkableCells(any())).thenReturn((new TargetCells()).setAllTargets(false));
        //setup obstacles in board
        spiedBoard.getCell(0, 0).getTower().placeComponent(Component.BLOCK);
        spiedBoard.getCell(1, 0).getTower().placeComponent(Component.BLOCK);
        spiedBoard.getCell(1, 0).getTower().placeComponent(Component.BLOCK);
        spiedBoard.getCell(1, 0).getTower().placeComponent(Component.BLOCK);
        spiedBoard.getCell(0, 1).getTower().placeComponent(Component.BLOCK);
        spiedBoard.getCell(0, 1).getTower().placeComponent(Component.BLOCK);
        spiedBoard.getCell(4, 4).getTower().placeComponent(Component.DOME);
        spiedBoard.getCell(2, 2).setWorker(mock(Worker.class));
        //the player methods
        when(this.mockPlayer.getOwnWorkers()).thenReturn(new Worker[]{this.mockWorker, mockWorker2});
        when(this.mockPlayer.getTurnEventsManager()).thenReturn(this.mockTurnEventsManager);
        //the game methods
        when(this.mockGame.getBoard()).thenReturn(spiedBoard);

        this.testMove.setup(this.mockTurn);

        //verify call on processBeforeMovementsBeforeMovementEvents(this.mockTurn);
        verify(this.mockTurnEventsManager, times(0)).processBeforeMovementEvents(this.mockTurn);

        //verify calls on allowedWorkers
        verify(this.mockTurn).addAllowedWorkers(this.mockPlayer.getOwnWorkers());
        //verify calls on target cells
        ArgumentCaptor<TargetCells> acTargetCell = ArgumentCaptor.forClass(TargetCells.class);
        ArgumentCaptor<Worker> acWorker = ArgumentCaptor.forClass(Worker.class);
        verify(this.mockTurn, times(2)).setWorkerWalkableCells(acWorker.capture(), acTargetCell.capture());

        //mockWorker 1 movements
        assertTrue(acWorker.getAllValues().contains(this.mockWorker));
        int index = acWorker.getAllValues().indexOf(this.mockWorker);
        assertEquals(acWorker.getAllValues().get(index), this.mockWorker);
        assertFalse(acTargetCell.getAllValues().get(index).getPosition(1, 0));
        assertFalse(acTargetCell.getAllValues().get(index).getPosition(0, 1));
        assertFalse(acTargetCell.getAllValues().get(index).getPosition(2, 2));
        assertTrue(acTargetCell.getAllValues().get(index).getPosition(0, 0));
        assertTrue(acTargetCell.getAllValues().get(index).getPosition(2, 0));
        assertTrue(acTargetCell.getAllValues().get(index).getPosition(2, 1));
        assertTrue(acTargetCell.getAllValues().get(index).getPosition(0, 2));
        assertTrue(acTargetCell.getAllValues().get(index).getPosition(1, 2));

        //mockWorker 2 movements
        assertTrue(acWorker.getAllValues().contains(mockWorker2));
        index = acWorker.getAllValues().indexOf(mockWorker2);
        assertEquals(acWorker.getAllValues().get(index), mockWorker2);
        assertFalse(acTargetCell.getAllValues().get(index).getPosition(4, 4));
        assertFalse(acTargetCell.getAllValues().get(index).getPosition(3, 3));
        assertFalse(acTargetCell.getAllValues().get(index).getPosition(0, 3));
        assertFalse(acTargetCell.getAllValues().get(index).getPosition(2, 2));
        assertTrue(acTargetCell.getAllValues().get(index).getPosition(4, 3));
        assertTrue(acTargetCell.getAllValues().get(index).getPosition(3, 4));
        assertTrue(acTargetCell.getAllValues().get(index).getPosition(2, 4));
        assertTrue(acTargetCell.getAllValues().get(index).getPosition(2, 3));
        assertTrue(acTargetCell.getAllValues().get(index).getPosition(3, 2));
        assertTrue(acTargetCell.getAllValues().get(index).getPosition(4, 2));

        //verify trigger losing condition
        verify(this.mockTurn).triggerLosingTurn();

    }

    @Test
    void setUpOneWorkerAllowedNoOccupiedCell() {
        //1 allowed worker scenario w/ faked win
        Board spiedBoard = spy(new Board());
        Worker mockWorker2 = mock(Worker.class);
        //worker 1 in cell 1 1
        spiedBoard.getCell(1, 1).setWorker(this.mockWorker);
        Cell cell1 = spiedBoard.getCell(1, 1);
        when(this.mockWorker.getCell()).thenReturn(cell1);
        //worker 2 in cell 3 3
        spiedBoard.getCell(3, 3).setWorker(mockWorker2);
        Cell cell2 = spiedBoard.getCell(3, 3);
        when(mockWorker2.getCell()).thenReturn(cell2);
        //the turn methods
        when(this.mockTurn.getPlayer()).thenReturn(this.mockPlayer);
        when(this.mockTurn.getGame()).thenReturn(this.mockGame);
        //non skippable turn
        when(this.mockTurn.isSkippable()).thenReturn(false);
        Cell cell3 = spiedBoard.getCell(0, 0);
        Cell cell4 = spiedBoard.getCell(1, 1);
        when(this.mockTurn.getPerformedAction()).thenReturn(new LinkedList<Action>(Arrays.asList(new MoveAction(cell3, cell4, cell3.getTower().getCurrentLevel(), cell4.getTower().getCurrentLevel(), this.mockWorker))));
        when(this.mockTurn.getAllowedWorkers()).thenReturn(new HashSet<Worker>(Arrays.asList(this.mockWorker)));
        when(this.mockTurn.getWorkerWalkableCells(any())).thenReturn((new TargetCells()).setAllTargets(true));
        //setup obstacles in board
        spiedBoard.getCell(0, 0).getTower().placeComponent(Component.BLOCK);
        spiedBoard.getCell(1, 0).getTower().placeComponent(Component.BLOCK);
        spiedBoard.getCell(1, 0).getTower().placeComponent(Component.BLOCK);
        spiedBoard.getCell(1, 0).getTower().placeComponent(Component.BLOCK);
        spiedBoard.getCell(0, 1).getTower().placeComponent(Component.BLOCK);
        spiedBoard.getCell(0, 1).getTower().placeComponent(Component.BLOCK);
        spiedBoard.getCell(4, 4).getTower().placeComponent(Component.DOME);
        spiedBoard.getCell(2, 2).setWorker(mock(Worker.class));
        //the player methods
        when(this.mockPlayer.getOwnWorkers()).thenReturn(new Worker[]{this.mockWorker, mockWorker2});
        when(this.mockPlayer.getTurnEventsManager()).thenReturn(this.mockTurnEventsManager);
        //the game methods
        when(this.mockGame.getBoard()).thenReturn(spiedBoard);

        this.testMove.setup(this.mockTurn);

        //verify call on processBeforeMovementsBeforeMovementEvents(this.mockTurn);
        verify(this.mockTurnEventsManager).processBeforeMovementEvents(this.mockTurn);

        //verify calls on allowedWorkers
        verify(this.mockTurn).addAllowedWorker(this.mockWorker);
        //verify calls on target cells
        ArgumentCaptor<TargetCells> acTargetCell = ArgumentCaptor.forClass(TargetCells.class);
        ArgumentCaptor<Worker> acWorker = ArgumentCaptor.forClass(Worker.class);
        verify(this.mockTurn, times(1)).setWorkerWalkableCells(acWorker.capture(), acTargetCell.capture());

        //mockWorker 1 movements
        assertEquals(acWorker.getValue(), this.mockWorker);
        assertFalse(acTargetCell.getValue().getPosition(1, 0));
        assertFalse(acTargetCell.getValue().getPosition(0, 1));
        assertFalse(acTargetCell.getValue().getPosition(2, 2));
        assertTrue(acTargetCell.getValue().getPosition(0, 0));
        assertTrue(acTargetCell.getValue().getPosition(2, 0));
        assertTrue(acTargetCell.getValue().getPosition(2, 1));
        assertTrue(acTargetCell.getValue().getPosition(0, 2));
        assertTrue(acTargetCell.getValue().getPosition(1, 2));

        //verify no losing turn
        verify(this.mockTurn, times(0)).triggerLosingTurn();
    }


    @Test
    void canMoveTo() {
        when(this.mockTurn.getWorkerWalkableCells(this.mockWorker)).thenReturn(this.mockTargetCells);
        when(this.mockTargetCells.getPosition(this.spiedCell.getX(), this.spiedCell.getY())).thenReturn(true).thenReturn(false);
        assertTrue(this.testMove.canMoveTo(this.mockWorker, this.spiedCell, this.mockTurn));
        assertFalse(this.testMove.canMoveTo(this.mockWorker, this.spiedCell, this.mockTurn));
        verify(this.mockTurn, times(2)).getWorkerWalkableCells(this.mockWorker);
        verify(this.mockTargetCells, times(2)).getPosition(this.spiedCell.getX(), this.spiedCell.getY());
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