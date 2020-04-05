package it.polimi.ingsw.model;

import it.polimi.ingsw.model.actions.BuildAction;
import it.polimi.ingsw.model.actions.MoveAction;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Component;
import it.polimi.ingsw.model.board.TargetCells;
import it.polimi.ingsw.model.turnstates.AbstractTurnState;
import it.polimi.ingsw.model.turnstates.TurnState;
import it.polimi.ingsw.model.workers.Worker;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TurnTest {

    @Test
    void setUpTestWithGetterAndSetters(){
        //Funny easter egg for JoJo fans
        Player myPlayer = spy(new Player("Giorno Giovanna"));
        //Mock game 'cause it's too complex
        Game mockGame = mock(Game.class);
        //A real board because why not
        Board myBoard = spy(new Board());
        //Give Game a meaning
        when(mockGame.getBoard()).thenReturn(myBoard);
        //Create the turn
        Turn myTurn = spy(new Turn(mockGame, myPlayer));

        //Let's see if we are in a neutral ground zan zan zan zan
        assertEquals(myTurn.getPlayer(),myPlayer);
        assertEquals(myTurn.getGame(),mockGame);
        assertTrue(myTurn.getPerformedAction().isEmpty());
        assertNotSame(myTurn.getPerformedAction(), myTurn.getPerformedAction());
        assertTrue(myTurn.getMoves().isEmpty());
        assertNotSame(myTurn.getMoves(), myTurn.getMoves());
        assertTrue(myTurn.getBuilds().isEmpty());
        assertNotSame(myTurn.getBuilds(), myTurn.getBuilds());
        assertTrue(myTurn.getAllowedWorkers().isEmpty());
        assertFalse(myTurn.isWinningTurn());
        assertFalse(myTurn.isLosingTurn());
        assertFalse(myTurn.isSkippable());
        assertFalse(myTurn.canEndTurn());

        //Now play a little with setters
        myTurn.setSkippable(true);
        assertTrue(myTurn.isSkippable());
        myTurn.setSkippable(false);
        assertFalse(myTurn.isSkippable());

        myTurn.setWinningTurn();
        assertTrue(myTurn.isWinningTurn());

        myTurn.addPerformedAction(new MoveAction(new Cell(0,0),new Cell(1,1),0,0,myPlayer.getOwnWorkers()[0]));
        myTurn.addPerformedAction(new BuildAction(new Cell(0,0), Component.BLOCK.getInstance(),5,myPlayer.getOwnWorkers()[0]));
        assertEquals(2, myTurn.getPerformedAction().size());
        assertEquals(1, myTurn.getMoves().size());
        assertNotNull(myTurn.getMoves().get(0));
        assertEquals(1, myTurn.getBuilds().size());
        assertNotNull(myTurn.getBuilds().get(0));

        myTurn.addAllowedWorkers(myPlayer.getOwnWorkers());
        assertEquals(2, myTurn.getAllowedWorkers().size());
        myTurn.clearAllowedWorkers();
        assertTrue(myTurn.getAllowedWorkers().isEmpty());
        myTurn.addAllowedWorker(myPlayer.getOwnWorkers()[0]);
        assertEquals(1, myTurn.getAllowedWorkers().size());

        TargetCells myTargetCells1 = new TargetCells();
        TargetCells myTargetCells2 = new TargetCells();
        myTurn.setWorkerWalkableCells(myPlayer.getOwnWorkers()[0],myTargetCells1);
        myTurn.setWorkerDomeBuildableCells(myPlayer.getOwnWorkers()[0],myTargetCells1);
        myTurn.setWorkerBlockBuildableCells(myPlayer.getOwnWorkers()[0],myTargetCells1);
        myTurn.setWorkerWalkableCells(myPlayer.getOwnWorkers()[1],myTargetCells2);
        myTurn.setWorkerDomeBuildableCells(myPlayer.getOwnWorkers()[1],myTargetCells2);
        myTurn.setWorkerBlockBuildableCells(myPlayer.getOwnWorkers()[1],myTargetCells2);
        assertEquals(myTargetCells1,myTurn.getWorkerWalkableCells(myPlayer.getOwnWorkers()[0]));
        assertEquals(myTargetCells1,myTurn.getWorkerDomeBuildableCells(myPlayer.getOwnWorkers()[0]));
        assertEquals(myTargetCells1,myTurn.getWorkerBlockBuildableCells(myPlayer.getOwnWorkers()[0]));
        assertEquals(myTargetCells2,myTurn.getWorkerWalkableCells(myPlayer.getOwnWorkers()[1]));
        assertEquals(myTargetCells2,myTurn.getWorkerDomeBuildableCells(myPlayer.getOwnWorkers()[1]));
        assertEquals(myTargetCells2,myTurn.getWorkerBlockBuildableCells(myPlayer.getOwnWorkers()[1]));

    }

    @Test
    void testLosingTurn(){

    }

    @Test
    void testWinningTurn(){

    }

    @Test
    void testStandardTurn(){

    }

    @Test
    void triggerLosingTurn() {
    }

    @Test
    void changeState() {
    }

    @Test
    void startTurn() {
    }

    @Test
    void canMoveTo() {
    }

    @Test
    void moveTo() {
    }

    @Test
    void canBuildDomeIn() {
    }

    @Test
    void buildDomeIn() {
    }

    @Test
    void canBuildBlockIn() {
    }

    @Test
    void buildBlockIn() {
    }

    @Test
    void draw() {
    }

    @Test
    void endTurn() {
    }
}