package it.polimi.ingsw.model.turnstates;

import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.actions.BuildAction;
import it.polimi.ingsw.model.actions.MoveAction;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Component;
import it.polimi.ingsw.model.board.TargetCells;
import it.polimi.ingsw.model.turnevents.TurnEventsManager;
import it.polimi.ingsw.model.workers.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuildTest {
    Build testBuild = new Build();
    Game mockGame;
    Turn mockTurn;
    Worker mockWorker;
    Cell spiedCell;
    TargetCells mockTargetCells;
    Player mockPlayer;
    TurnEventsManager mockTurnEventsManager;
    User mockUser;

    @BeforeEach
    void reset() {
        this.mockGame = spy(new Game(2));
        this.mockTurn = mock(Turn.class);
        this.mockWorker = mock(Worker.class);
        this.spiedCell = spy(new Cell(0, 0));
        this.mockTargetCells = mock(TargetCells.class);
        this.mockPlayer = spy(new Player("Giosuè"));
        this.mockTurnEventsManager = mock(TurnEventsManager.class);
        this.mockUser = spy(new User(mockPlayer.getNickname()));
        mockGame.subscribeUser(mockUser);
        when(mockGame.getUserFromPlayer(mockPlayer)).thenReturn(mockUser);
        when(mockGame.getPlayerFromUser(mockUser)).thenReturn(mockPlayer);
    }

    @Test
    void setupWithAllWorkerAllowedAndNoOccupiedCell() {
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
        when(this.mockTurn.getPerformedAction()).thenReturn(new LinkedList<>());
        when(this.mockTurn.getAllowedWorkers()).thenReturn(new HashSet<>(Arrays.asList(this.mockWorker, mockWorker2)));
        when(this.mockTurn.getWorkerDomeBuildableCells(any())).thenReturn((new TargetCells()).setAllTargets(false));
        when(this.mockTurn.getWorkerBlockBuildableCells(any())).thenReturn((new TargetCells()).setAllTargets(false));
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
        when(this.mockPlayer.getWorkers()).thenReturn(new Worker[]{this.mockWorker, mockWorker2});
        when(this.mockPlayer.getTurnEventsManager()).thenReturn(this.mockTurnEventsManager);
        //the game methods
        when(this.mockGame.getBoard()).thenReturn(spiedBoard);

        this.testBuild.setup(this.mockTurn);

        //verify call on processBeforeMovementsBeforeBuildEvents(this.mockTurn);
        verify(this.mockTurnEventsManager, times(1)).processBeforeBuildEvents(this.mockTurn);

        //verify calls on allowedWorkers
        verify(this.mockTurn).addAllowedWorkers(this.mockPlayer.getWorkers());
        //verify calls on target cells
        ArgumentCaptor<TargetCells> acDomeTargetCell = ArgumentCaptor.forClass(TargetCells.class);
        ArgumentCaptor<Worker> acDomeWorker = ArgumentCaptor.forClass(Worker.class);
        verify(this.mockTurn, times(2)).setWorkerDomeBuildableCells(acDomeWorker.capture(), acDomeTargetCell.capture());
        ArgumentCaptor<TargetCells> acBlockTargetCell = ArgumentCaptor.forClass(TargetCells.class);
        ArgumentCaptor<Worker> acBlockWorker = ArgumentCaptor.forClass(Worker.class);
        verify(this.mockTurn, times(2)).setWorkerBlockBuildableCells(acBlockWorker.capture(), acBlockTargetCell.capture());

        //mockWorker 1 builds
        assertTrue(acDomeWorker.getAllValues().contains(this.mockWorker));
        int index = acDomeWorker.getAllValues().indexOf(this.mockWorker);
        assertEquals(acDomeWorker.getAllValues().get(index), this.mockWorker);
        assertFalse(acDomeTargetCell.getAllValues().get(index).getPosition(0, 1));
        assertFalse(acDomeTargetCell.getAllValues().get(index).getPosition(2, 2));
        assertFalse(acDomeTargetCell.getAllValues().get(index).getPosition(0, 0));
        assertFalse(acDomeTargetCell.getAllValues().get(index).getPosition(2, 0));
        assertFalse(acDomeTargetCell.getAllValues().get(index).getPosition(2, 1));
        assertFalse(acDomeTargetCell.getAllValues().get(index).getPosition(0, 2));
        assertFalse(acDomeTargetCell.getAllValues().get(index).getPosition(1, 2));
        assertTrue(acDomeTargetCell.getAllValues().get(index).getPosition(1, 0));

        assertTrue(acBlockWorker.getAllValues().contains(this.mockWorker));
        index = acBlockWorker.getAllValues().indexOf(this.mockWorker);
        assertEquals(acBlockWorker.getAllValues().get(index), this.mockWorker);
        assertFalse(acBlockTargetCell.getAllValues().get(index).getPosition(1, 0));
        assertFalse(acBlockTargetCell.getAllValues().get(index).getPosition(2, 2));
        assertTrue(acBlockTargetCell.getAllValues().get(index).getPosition(0, 1));
        assertTrue(acBlockTargetCell.getAllValues().get(index).getPosition(0, 0));
        assertTrue(acBlockTargetCell.getAllValues().get(index).getPosition(2, 0));
        assertTrue(acBlockTargetCell.getAllValues().get(index).getPosition(2, 1));
        assertTrue(acBlockTargetCell.getAllValues().get(index).getPosition(0, 2));
        assertTrue(acBlockTargetCell.getAllValues().get(index).getPosition(1, 2));

        //mockWorker 2 builds
        assertTrue(acDomeWorker.getAllValues().contains(mockWorker2));
        index = acDomeWorker.getAllValues().indexOf(mockWorker2);
        assertEquals(acDomeWorker.getAllValues().get(index), mockWorker2);
        assertFalse(acDomeTargetCell.getAllValues().get(index).getPosition(4, 4));
        assertFalse(acDomeTargetCell.getAllValues().get(index).getPosition(3, 3));
        assertFalse(acDomeTargetCell.getAllValues().get(index).getPosition(0, 3));
        assertFalse(acDomeTargetCell.getAllValues().get(index).getPosition(2, 2));
        assertFalse(acDomeTargetCell.getAllValues().get(index).getPosition(4, 3));
        assertFalse(acDomeTargetCell.getAllValues().get(index).getPosition(3, 4));
        assertFalse(acDomeTargetCell.getAllValues().get(index).getPosition(2, 4));
        assertFalse(acDomeTargetCell.getAllValues().get(index).getPosition(2, 3));
        assertFalse(acDomeTargetCell.getAllValues().get(index).getPosition(3, 2));
        assertFalse(acDomeTargetCell.getAllValues().get(index).getPosition(4, 2));

        assertTrue(acBlockWorker.getAllValues().contains(mockWorker2));
        index = acBlockWorker.getAllValues().indexOf(mockWorker2);
        assertEquals(acBlockWorker.getAllValues().get(index), mockWorker2);
        assertFalse(acBlockTargetCell.getAllValues().get(index).getPosition(4, 4));
        assertFalse(acBlockTargetCell.getAllValues().get(index).getPosition(2, 2));
        assertFalse(acBlockTargetCell.getAllValues().get(index).getPosition(3, 3));
        assertFalse(acBlockTargetCell.getAllValues().get(index).getPosition(0, 3));
        assertTrue(acBlockTargetCell.getAllValues().get(index).getPosition(4, 3));
        assertTrue(acBlockTargetCell.getAllValues().get(index).getPosition(3, 4));
        assertTrue(acBlockTargetCell.getAllValues().get(index).getPosition(2, 4));
        assertTrue(acBlockTargetCell.getAllValues().get(index).getPosition(2, 3));
        assertTrue(acBlockTargetCell.getAllValues().get(index).getPosition(3, 2));
        assertTrue(acBlockTargetCell.getAllValues().get(index).getPosition(4, 2));

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
        when(this.mockTurn.getPerformedAction()).thenReturn(new LinkedList<>(Collections.singletonList(new MoveAction(cell3, cell4, cell3.getTower().getCurrentLevel(), cell4.getTower().getCurrentLevel(), this.mockWorker))));
        when(this.mockTurn.getAllowedWorkers()).thenReturn(new HashSet<>(Collections.singletonList(this.mockWorker)));
        when(this.mockTurn.getWorkerDomeBuildableCells(any())).thenReturn((new TargetCells()).setAllTargets(true));
        when(this.mockTurn.getWorkerBlockBuildableCells(any())).thenReturn((new TargetCells()).setAllTargets(true));
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
        when(this.mockPlayer.getWorkers()).thenReturn(new Worker[]{this.mockWorker, mockWorker2});
        when(this.mockPlayer.getTurnEventsManager()).thenReturn(this.mockTurnEventsManager);
        //the game methods
        when(this.mockGame.getBoard()).thenReturn(spiedBoard);

        this.testBuild.setup(this.mockTurn);

        //verify call on processBeforeBuildBeforeMovementEvents(this.mockTurn);
        verify(this.mockTurnEventsManager).processBeforeBuildEvents(this.mockTurn);

        //verify calls on allowedWorkers
        verify(this.mockTurn).addAllowedWorker(this.mockWorker);
        //verify calls on target cells
        ArgumentCaptor<TargetCells> acDomeTargetCell = ArgumentCaptor.forClass(TargetCells.class);
        ArgumentCaptor<Worker> acDomeWorker = ArgumentCaptor.forClass(Worker.class);
        verify(this.mockTurn, times(2)).setWorkerDomeBuildableCells(acDomeWorker.capture(), acDomeTargetCell.capture());
        ArgumentCaptor<TargetCells> acBlockTargetCell = ArgumentCaptor.forClass(TargetCells.class);
        ArgumentCaptor<Worker> acBlockWorker = ArgumentCaptor.forClass(Worker.class);
        verify(this.mockTurn, times(2)).setWorkerBlockBuildableCells(acBlockWorker.capture(), acBlockTargetCell.capture());

        //mockWorker 1 builds
        int index = acBlockWorker.getAllValues().indexOf(mockWorker);
        TargetCells domeTargetCell = acDomeTargetCell.getAllValues().get(index);
        assertTrue(acDomeWorker.getAllValues().contains(this.mockWorker));
        assertFalse(domeTargetCell.getPosition(0, 1));
        assertFalse(domeTargetCell.getPosition(2, 2));
        assertFalse(domeTargetCell.getPosition(0, 0));
        assertFalse(domeTargetCell.getPosition(2, 0));
        assertFalse(domeTargetCell.getPosition(2, 1));
        assertFalse(domeTargetCell.getPosition(0, 2));
        assertFalse(domeTargetCell.getPosition(1, 2));
        assertTrue(domeTargetCell.getPosition(1, 0));

        index = acBlockWorker.getAllValues().indexOf(mockWorker);
        assertTrue(acBlockWorker.getAllValues().contains(this.mockWorker));
        TargetCells blockTargetCell = acBlockTargetCell.getAllValues().get(index);
        assertFalse(blockTargetCell.getPosition(1, 0));
        assertFalse(blockTargetCell.getPosition(2, 2));
        assertTrue(blockTargetCell.getPosition(0, 1));
        assertTrue(blockTargetCell.getPosition(0, 0));
        assertTrue(blockTargetCell.getPosition(2, 0));
        assertTrue(blockTargetCell.getPosition(2, 1));
        assertTrue(blockTargetCell.getPosition(0, 2));
        assertTrue(blockTargetCell.getPosition(1, 2));

        //verify no losing turn
        verify(this.mockTurn, times(0)).triggerLosingTurn();
    }


    @Test
    void canBuildDomeIn() {
        when(mockTurn.getAllowedWorkers()).thenReturn(new HashSet<>(Collections.singletonList(mockWorker)));
        when(mockTurn.getWorkerDomeBuildableCells(mockWorker)).thenReturn(mockTargetCells);
        when(mockTargetCells.getPosition(spiedCell.getX(), spiedCell.getY())).thenReturn(true).thenReturn(false);
        assertTrue(this.testBuild.canBuildDomeIn(this.mockWorker, this.spiedCell, this.mockTurn));
        assertFalse(this.testBuild.canBuildDomeIn(this.mockWorker, this.spiedCell, this.mockTurn));
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
        when(mockTurn.getAllowedWorkers()).thenReturn(new HashSet<>(Collections.singletonList(mockWorker)));
        when(mockTurn.getWorkerBlockBuildableCells(mockWorker)).thenReturn(mockTargetCells);
        when(mockTargetCells.getPosition(spiedCell.getX(), spiedCell.getY())).thenReturn(true).thenReturn(false);
        assertTrue(this.testBuild.canBuildBlockIn(this.mockWorker, this.spiedCell, this.mockTurn));
        assertFalse(this.testBuild.canBuildBlockIn(this.mockWorker, this.spiedCell, this.mockTurn));
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