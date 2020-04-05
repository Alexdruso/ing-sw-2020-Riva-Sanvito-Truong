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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MoveTest {
    Move testMove = new Move();
    Game mockGame;
    Turn mockTurn;
    Worker mockWorker;
    Cell spiedCell ;
    TargetCells mockTargetCells;
    Player mockPlayer;
    TurnEventsManager mockTurnEventsManager;

    @BeforeEach
    void reset(){
        this.mockGame = mock(Game.class);
        this.mockTurn = mock(Turn.class);
        this.mockWorker = mock(Worker.class);
        this.spiedCell = spy(new Cell(0,0));
        this.mockTargetCells = mock(TargetCells.class);
        this.mockPlayer = mock(Player.class);
        this.mockTurnEventsManager = mock(TurnEventsManager.class);
    }

    @Test
    void setupWithAllWorkerAllowed() {
        //2 workers scenario
        Worker mockWorker2 = mock(Worker.class);
        Board spiedBoard = spy(new Board());
        TargetCells[] spiedTargetCells = {new TargetCells(),new TargetCells()};
        //worker 1 in cell 1 1
        spiedBoard.getCell(1,1).setWorker(this.mockWorker);
        when(this.mockWorker.getCell()).thenReturn(spiedBoard.getCell(1,1));
        //worker 2 in cell 3 3
        spiedBoard.getCell(3,3).setWorker(mockWorker2);
        when(mockWorker2.getCell()).thenReturn(spiedBoard.getCell(3,3));
        //the turn methods
        when(this.mockTurn.getPlayer()).thenReturn(this.mockPlayer);
        when(this.mockTurn.getGame()).thenReturn(this.mockGame);
        when(this.mockTurn.getWorkerWalkableCells(this.mockWorker)).thenReturn(spiedTargetCells[0]);
        when(this.mockTurn.getWorkerWalkableCells(mockWorker2)).thenReturn(spiedTargetCells[1]);
        //non skippable turn
        when(this.mockTurn.isSkippable()).thenReturn(false);
        when(this.mockTurn.getPerformedAction()).thenReturn(new LinkedList<Action>());
        when(this.mockTurn.getAllowedWorkers()).thenReturn(new HashSet<Worker>(Arrays.asList(this.mockWorker, mockWorker2)));
        //setup obstacles in board
        spiedBoard.getCell(0,0).getTower().placeComponent(Component.BLOCK);
        spiedBoard.getCell(1,0).getTower().placeComponent(Component.BLOCK);
        spiedBoard.getCell(1,0).getTower().placeComponent(Component.BLOCK);
        spiedBoard.getCell(1,0).getTower().placeComponent(Component.BLOCK);
        spiedBoard.getCell(0,1).getTower().placeComponent(Component.BLOCK);
        spiedBoard.getCell(0,1).getTower().placeComponent(Component.BLOCK);
        spiedBoard.getCell(4,4).getTower().placeComponent(Component.DOME);
        //the player methods
        when(this.mockPlayer.getOwnWorkers()).thenReturn(new Worker[]{this.mockWorker, mockWorker2});
        when(this.mockPlayer.getTurnEventsManager()).thenReturn(this.mockTurnEventsManager);
        //the game methods
        when(this.mockGame.getBoard()).thenReturn(spiedBoard);

        this.testMove.setup(this.mockTurn);

    }

    @Test
    void setUpOneWorkerAllowedNoOccupiedCell(){

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