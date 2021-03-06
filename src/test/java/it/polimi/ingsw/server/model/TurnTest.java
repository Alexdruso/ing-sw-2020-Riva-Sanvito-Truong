package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.controller.User;
import it.polimi.ingsw.server.model.actions.BuildAction;
import it.polimi.ingsw.server.model.actions.MoveAction;
import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Cell;
import it.polimi.ingsw.server.model.board.Component;
import it.polimi.ingsw.server.model.board.TargetCells;
import it.polimi.ingsw.server.model.turnevents.TurnEventsManager;
import it.polimi.ingsw.server.model.turnstates.InvalidTurnStateException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TurnTest {

    @Test
    void setUpTestWithGetterAndSetters() {
        //Our own default TurnEventsManager
        TurnEventsManager myTurnEventsManager = mock(TurnEventsManager.class);
        //Funny easter egg for JoJo fans
        Player myPlayer = spy(new Player("Giorno Giovanna"));
        //The trick to make it work without a god
        when(myPlayer.getTurnEventsManager()).thenReturn(myTurnEventsManager);
        //Mock game 'cause it's too complex
        Game mockGame = mock(Game.class);
        //A real board because why not
        Board myBoard = spy(new Board());
        //Give Game a meaning
        when(mockGame.getBoard()).thenReturn(myBoard);
        //Setup player's workers now that we invoke move.setup before starting the turn
        myPlayer.getWorkers()[0].setCell(myBoard.getCell(1, 1));
        myPlayer.getWorkers()[1].setCell(myBoard.getCell(3, 3));
        //Create the turn
        Turn myTurn = spy(new Turn(mockGame, myPlayer));

        //Let's see if we are in a neutral ground zan zan zan zan
        assertEquals(myTurn.getPlayer(), myPlayer);
        assertEquals(myTurn.getGame(), mockGame);
        assertTrue(myTurn.getPerformedAction().isEmpty());
        assertNotSame(myTurn.getPerformedAction(), myTurn.getPerformedAction());
        assertTrue(myTurn.getMoves().isEmpty());
        assertNotSame(myTurn.getMoves(), myTurn.getMoves());
        assertTrue(myTurn.getBuilds().isEmpty());
        assertNotSame(myTurn.getBuilds(), myTurn.getBuilds());
        //assertTrue(myTurn.getAllowedWorkers().isEmpty());
        //assertFalse(myTurn.isWinningTurn());
        //assertFalse(myTurn.isLosingTurn());
        //assertFalse(myTurn.isSkippable());

        //Now play a little with setters
        myTurn.setSkippable(true);
        assertTrue(myTurn.isSkippable());
        myTurn.setSkippable(false);
        assertFalse(myTurn.isSkippable());

        myTurn.setWinningTurn();
        assertTrue(myTurn.isWinningTurn());


        //now just try to revert things
        myTurn.setNeutralTurn();
        assertFalse(myTurn.isLosingTurn());
        assertFalse(myTurn.isWinningTurn());

        myTurn.addPerformedAction(new MoveAction(new Cell(0, 0), new Cell(1, 1), 0, 0, myPlayer.getWorkers()[0]));
        myTurn.addPerformedAction(new BuildAction(new Cell(0, 0), Component.BLOCK.getInstance(), 5, myPlayer.getWorkers()[0]));
        assertEquals(2, myTurn.getPerformedAction().size());
        assertEquals(1, myTurn.getMoves().size());
        assertNotNull(myTurn.getMoves().get(0));
        assertEquals(1, myTurn.getBuilds().size());
        assertNotNull(myTurn.getBuilds().get(0));

        myTurn.addAllowedWorkers(myPlayer.getWorkers());
        assertEquals(2, myTurn.getAllowedWorkers().size());
        myTurn.clearAllowedWorkers();
        assertTrue(myTurn.getAllowedWorkers().isEmpty());
        myTurn.addAllowedWorker(myPlayer.getWorkers()[0]);
        assertEquals(1, myTurn.getAllowedWorkers().size());
        myTurn.clearAllowedWorkers();
        assertTrue(myTurn.getAllowedWorkers().isEmpty());
        myTurn.addAllowedWorkers(Arrays.asList(myPlayer.getWorkers()));
        assertEquals(2, myTurn.getAllowedWorkers().size());
        myTurn.clearAllowedWorkers();
        assertTrue(myTurn.getAllowedWorkers().isEmpty());

        TargetCells myTargetCells1 = new TargetCells();
        TargetCells myTargetCells2 = new TargetCells();
        myTurn.setWorkerWalkableCells(myPlayer.getWorkers()[0], myTargetCells1);
        myTurn.setWorkerDomeBuildableCells(myPlayer.getWorkers()[0], myTargetCells1);
        myTurn.setWorkerBlockBuildableCells(myPlayer.getWorkers()[0], myTargetCells1);
        myTurn.setWorkerWalkableCells(myPlayer.getWorkers()[1], myTargetCells2);
        myTurn.setWorkerDomeBuildableCells(myPlayer.getWorkers()[1], myTargetCells2);
        myTurn.setWorkerBlockBuildableCells(myPlayer.getWorkers()[1], myTargetCells2);
        assertEquals(myTargetCells1, myTurn.getWorkerWalkableCells(myPlayer.getWorkers()[0]));
        assertEquals(myTargetCells1, myTurn.getWorkerDomeBuildableCells(myPlayer.getWorkers()[0]));
        assertEquals(myTargetCells1, myTurn.getWorkerBlockBuildableCells(myPlayer.getWorkers()[0]));
        assertEquals(myTargetCells2, myTurn.getWorkerWalkableCells(myPlayer.getWorkers()[1]));
        assertEquals(myTargetCells2, myTurn.getWorkerDomeBuildableCells(myPlayer.getWorkers()[1]));
        assertEquals(myTargetCells2, myTurn.getWorkerBlockBuildableCells(myPlayer.getWorkers()[1]));

    }

    @Test
    void testLosingTurn() throws InvalidTurnStateException {
        //Our own default TurnEventsManager
        TurnEventsManager myTurnEventsManager = mock(TurnEventsManager.class);
        //Funny easter egg for JoJo fans
        Player myPlayer = spy(new Player("Giorno Giovanna"));
        //The trick to make it work without a god
        when(myPlayer.getTurnEventsManager()).thenReturn(myTurnEventsManager);
        //Mock game 'cause it's too complex
        Game myGame = spy(new Game(2));
        //A real board because why not
        Board myBoard = spy(new Board());
        //A user to subscribe to the game
        User myUser = spy(new User(myPlayer.getNickname()));
        //Another player w/ his own User
        Player myPlayer2 = spy(new Player("Il signor kekkeroni"));
        User myUser2 = spy(new User(myPlayer2.getNickname()));
        TurnEventsManager myTurnEventsManager2 = mock(TurnEventsManager.class);
        when(myPlayer2.getTurnEventsManager()).thenReturn(myTurnEventsManager2);
        //Give Game a meaning
        when(myGame.getBoard()).thenReturn(myBoard);
        doNothing().when(myGame).triggerLosingTurn();
        myGame.subscribeUser(myUser);
        myGame.subscribeUser(myUser2);
        when(myGame.getUserFromPlayer(myPlayer)).thenReturn(myUser);
        when(myGame.getPlayerFromUser(myUser)).thenReturn(myPlayer);
        when(myGame.getUserFromPlayer(myPlayer2)).thenReturn(myUser2);
        when(myGame.getPlayerFromUser(myUser2)).thenReturn(myPlayer2);

        //Setup the state to lose
        myPlayer.getWorkers()[0].setCell(myBoard.getCell(1, 1));
        myBoard.getCell(1, 1).setWorker(myPlayer.getWorkers()[0]);
        myBoard.getCell(0, 0).getTower().placeComponent(Component.DOME);
        myBoard.getCell(1, 0).getTower().placeComponent(Component.DOME);
        myBoard.getCell(2, 0).getTower().placeComponent(Component.DOME);
        myBoard.getCell(0, 1).getTower().placeComponent(Component.DOME);
        myBoard.getCell(2, 1).getTower().placeComponent(Component.DOME);
        myBoard.getCell(0, 2).getTower().placeComponent(Component.DOME);
        myBoard.getCell(1, 2).getTower().placeComponent(Component.DOME);
        myBoard.getCell(2, 2).getTower().placeComponent(Component.DOME);
        myPlayer.getWorkers()[1].setCell(myBoard.getCell(3, 3));
        myBoard.getCell(3, 3).setWorker(myPlayer.getWorkers()[1]);
        myBoard.getCell(3, 2).getTower().placeComponent(Component.DOME);
        myBoard.getCell(4, 2).getTower().placeComponent(Component.DOME);
        myBoard.getCell(2, 3).getTower().placeComponent(Component.DOME);
        myBoard.getCell(4, 3).getTower().placeComponent(Component.DOME);
        myBoard.getCell(2, 4).getTower().placeComponent(Component.DOME);
        myBoard.getCell(3, 4).getTower().placeComponent(Component.DOME);
        myBoard.getCell(4, 4).getTower().placeComponent(Component.DOME);
        //Create the turn
        Turn myTurn = spy(new Turn(myGame, myPlayer));
        //Check if moving throws exception -> we are in lose state
        assertThrows(InvalidTurnStateException.class, () -> myTurn.moveTo(myPlayer.getWorkers()[0], myBoard.getCell(0, 0)));
        //Check if it is a losing turn :(
        assertTrue(myTurn.isLosingTurn());
    }

    @Test
    void testWinningTurn() throws InvalidTurnStateException {
        //Our own default TurnEventsManager
        TurnEventsManager myTurnEventsManager = mock(TurnEventsManager.class);
        //Funny easter egg for JoJo fans
        Player myPlayer = new Player("Giorno Giovanna");
        Player mySpiedPlayer = spy(myPlayer);
        //The trick to make it work without a god
        when(mySpiedPlayer.getTurnEventsManager()).thenReturn(myTurnEventsManager);
        //Mock game 'cause it's too complex
        Game myGame = spy(new Game(2));
        //A real board because why not
        Board myBoard = spy(new Board());
        //A user
        User myUser = spy(new User(mySpiedPlayer.getNickname()));
        //Give Game a meaning
        when(myGame.getBoard()).thenReturn(myBoard);
        myGame.subscribeUser(myUser);
        when(myGame.getUserFromPlayer(myPlayer)).thenReturn(myUser);
        when(myGame.getUserFromPlayer(mySpiedPlayer)).thenReturn(myUser);
        when(myGame.getPlayerFromUser(myUser)).thenReturn(myPlayer);

        //Setup the state to win
        mySpiedPlayer.getWorkers()[0].setCell(myBoard.getCell(1, 1));
        myBoard.getCell(1, 1).setWorker(mySpiedPlayer.getWorkers()[0]);
        myBoard.getCell(1, 1).getTower().placeComponent(Component.BLOCK);
        myBoard.getCell(1, 1).getTower().placeComponent(Component.BLOCK);
        myBoard.getCell(0, 0).getTower().placeComponent(Component.BLOCK);
        myBoard.getCell(0, 0).getTower().placeComponent(Component.BLOCK);
        myBoard.getCell(0, 0).getTower().placeComponent(Component.BLOCK);
        mySpiedPlayer.getWorkers()[1].setCell(myBoard.getCell(3, 3));
        myBoard.getCell(3, 3).setWorker(mySpiedPlayer.getWorkers()[1]);
        //Create the turn
        Turn myTurn = spy(new Turn(myGame, mySpiedPlayer));
        //Just another check that all is right
        assertFalse(myTurn.canMoveTo(mySpiedPlayer.getWorkers()[1], myBoard.getCell(0, 0)));
        //Now we check if we can move :)
        assertTrue(myTurn.canMoveTo(mySpiedPlayer.getWorkers()[0], myBoard.getCell(0, 0)));
        //Now move to win!
        myTurn.moveTo(mySpiedPlayer.getWorkers()[0], myBoard.getCell(0, 0));
        //Check if movement happened
        assertEquals(mySpiedPlayer.getWorkers()[0].getCell(), myBoard.getCell(0, 0));
        assertEquals(mySpiedPlayer.getWorkers()[0], myBoard.getCell(0, 0).getWorker().get());
        assertEquals(1, myTurn.getMoves().size());
        //Check if movement was registered
        assertEquals(myTurn.getPerformedAction().get(0).getWorker(), mySpiedPlayer.getWorkers()[0]);
        //Check no more allowed workers
        assertEquals(0, myTurn.getAllowedWorkers().size());
        //Check is winning turn
        assertTrue(myTurn.isWinningTurn());
    }

    @Test
    void testStandardTurn() throws InvalidTurnStateException {
        //Our own default TurnEventsManager
        TurnEventsManager myTurnEventsManager = mock(TurnEventsManager.class);
        //Funny easter egg for JoJo fans
        Player myPlayer = new Player("Giorno Giovanna");
        Player mySpiedPlayer = spy(myPlayer);
        //The trick to make it work without a god
        when(mySpiedPlayer.getTurnEventsManager()).thenReturn(myTurnEventsManager);
        //Mock game 'cause it's too complex
        Game myGame = spy(new Game(2));
        //A real board because why not
        Board myBoard = spy(new Board());
        //A user to subscribe to the game
        User myUser = spy(new User(mySpiedPlayer.getNickname()));
        //Another player w/ his own User
        Player myPlayer2 = new Player("Il signor kekkeroni");
        Player mySpiedPlayer2 = spy(myPlayer2);
        User myUser2 = spy(new User(mySpiedPlayer2.getNickname()));
        TurnEventsManager myTurnEventsManager2 = mock(TurnEventsManager.class);
        when(mySpiedPlayer2.getTurnEventsManager()).thenReturn(myTurnEventsManager2);
        //Give Game a meaning
        when(myGame.getBoard()).thenReturn(myBoard);
        doNothing().when(myGame).triggerEndTurn();
        myGame.subscribeUser(myUser);
        myGame.subscribeUser(myUser2);
        when(myGame.getUserFromPlayer(myPlayer)).thenReturn(myUser);
        when(myGame.getUserFromPlayer(mySpiedPlayer)).thenReturn(myUser);
        when(myGame.getPlayerFromUser(myUser)).thenReturn(mySpiedPlayer);
        when(myGame.getUserFromPlayer(myPlayer2)).thenReturn(myUser2);
        when(myGame.getUserFromPlayer(mySpiedPlayer2)).thenReturn(myUser2);
        when(myGame.getPlayerFromUser(myUser2)).thenReturn(mySpiedPlayer2);


        //Setup the state
        mySpiedPlayer.getWorkers()[0].setCell(myBoard.getCell(1, 1));
        myBoard.getCell(1, 1).setWorker(mySpiedPlayer.getWorkers()[0]);
        mySpiedPlayer.getWorkers()[1].setCell(myBoard.getCell(3, 3));
        myBoard.getCell(3, 3).setWorker(mySpiedPlayer.getWorkers()[1]);
        //Create the turn
        Turn myTurn = spy(new Turn(myGame, mySpiedPlayer));
        //Just another check that all is right
        assertFalse(myTurn.canMoveTo(mySpiedPlayer.getWorkers()[1], myBoard.getCell(0, 0)));
        //Now we check if we can move :)
        assertTrue(myTurn.canMoveTo(mySpiedPlayer.getWorkers()[0], myBoard.getCell(0, 0)));
        //Now move
        myTurn.moveTo(mySpiedPlayer.getWorkers()[0], myBoard.getCell(0, 0));
        //Check if movement happened
        assertEquals(mySpiedPlayer.getWorkers()[0].getCell(), myBoard.getCell(0, 0));
        assertEquals(mySpiedPlayer.getWorkers()[0], myBoard.getCell(0, 0).getWorker().get());
        assertEquals(1, myTurn.getMoves().size());
        //Check if movement was registered
        assertEquals(myTurn.getPerformedAction().get(0).getWorker(), mySpiedPlayer.getWorkers()[0]);
        //Check that worker 2 is not in allowedWorkers anymore
        assertFalse(myTurn.getAllowedWorkers().contains(mySpiedPlayer.getWorkers()[1]));
        //Trigger exception
        assertThrows(InvalidTurnStateException.class, () -> myTurn.moveTo(mySpiedPlayer.getWorkers()[0], myBoard.getCell(0, 0)));
        //Now we check and build a block where we were
        assertTrue(myTurn.canBuildBlockIn(mySpiedPlayer.getWorkers()[0], myBoard.getCell(1, 1)));
        myTurn.buildBlockIn(mySpiedPlayer.getWorkers()[0], myBoard.getCell(1, 1));
        //Check the build happened
        assertEquals(1, myBoard.getCell(1, 1).getTower().getCurrentLevel());
        assertEquals(myTurn.getPerformedAction().get(1).getWorker(), mySpiedPlayer.getWorkers()[0]);
        assertEquals(1, myTurn.getBuilds().size());
        //Check no more allowed workers
        assertEquals(0, myTurn.getAllowedWorkers().size());
    }

    @Test
    void testStandardTurnWithDomeBuild() throws InvalidTurnStateException {
        //Our own default TurnEventsManager
        TurnEventsManager myTurnEventsManager = mock(TurnEventsManager.class);
        //Funny easter egg for JoJo fans
        Player myPlayer = new Player("Giorno Giovanna");
        Player mySpiedPlayer = spy(myPlayer);
        //The trick to make it work without a god
        when(mySpiedPlayer.getTurnEventsManager()).thenReturn(myTurnEventsManager);
        //Mock game 'cause it's too complex
        Game myGame = spy(new Game(2));
        //A real board because why not
        Board myBoard = spy(new Board());
        //A user to subscribe to the game
        User myUser = spy(new User(mySpiedPlayer.getNickname()));
        //Another player w/ his own User
        Player myPlayer2 = new Player("Il signor kekkeroni");
        Player mySpiedPlayer2 = spy(myPlayer2);
        User myUser2 = spy(new User(mySpiedPlayer2.getNickname()));
        TurnEventsManager myTurnEventsManager2 = mock(TurnEventsManager.class);
        when(mySpiedPlayer2.getTurnEventsManager()).thenReturn(myTurnEventsManager2);
        //Give Game a meaning
        when(myGame.getBoard()).thenReturn(myBoard);
        doNothing().when(myGame).triggerEndTurn();
        myGame.subscribeUser(myUser);
        myGame.subscribeUser(myUser2);
        when(myGame.getUserFromPlayer(myPlayer)).thenReturn(myUser);
        when(myGame.getUserFromPlayer(mySpiedPlayer)).thenReturn(myUser);
        when(myGame.getPlayerFromUser(myUser)).thenReturn(mySpiedPlayer);
        when(myGame.getUserFromPlayer(myPlayer2)).thenReturn(myUser2);
        when(myGame.getUserFromPlayer(mySpiedPlayer2)).thenReturn(myUser2);
        when(myGame.getPlayerFromUser(myUser2)).thenReturn(mySpiedPlayer2);

        //Setup the state
        mySpiedPlayer.getWorkers()[0].setCell(myBoard.getCell(1, 1));
        myBoard.getCell(1, 1).setWorker(mySpiedPlayer.getWorkers()[0]);
        myBoard.getCell(1, 0).getTower().placeComponent(Component.BLOCK);
        myBoard.getCell(1, 0).getTower().placeComponent(Component.BLOCK);
        myBoard.getCell(1, 0).getTower().placeComponent(Component.BLOCK);
        mySpiedPlayer.getWorkers()[1].setCell(myBoard.getCell(3, 3));
        myBoard.getCell(3, 3).setWorker(mySpiedPlayer.getWorkers()[1]);
        //Create the turn
        Turn myTurn = spy(new Turn(myGame, mySpiedPlayer));
        //Just another check that all is right
        assertFalse(myTurn.canMoveTo(mySpiedPlayer.getWorkers()[1], myBoard.getCell(0, 0)));
        //Now we check if we can move :)
        assertTrue(myTurn.canMoveTo(mySpiedPlayer.getWorkers()[0], myBoard.getCell(0, 0)));
        //Now move
        myTurn.moveTo(mySpiedPlayer.getWorkers()[0], myBoard.getCell(0, 0));
        //Check if movement happened
        assertEquals(mySpiedPlayer.getWorkers()[0].getCell(), myBoard.getCell(0, 0));
        assertEquals(mySpiedPlayer.getWorkers()[0], myBoard.getCell(0, 0).getWorker().get());
        assertEquals(1, myTurn.getMoves().size());
        //Check if movement was registered
        assertEquals(myTurn.getPerformedAction().get(0).getWorker(), mySpiedPlayer.getWorkers()[0]);
        //Check that worker 2 is not in allowedWorkers anymore
        assertFalse(myTurn.getAllowedWorkers().contains(mySpiedPlayer.getWorkers()[1]));
        //Trigger exception
        assertThrows(InvalidTurnStateException.class, () -> myTurn.moveTo(mySpiedPlayer.getWorkers()[0], myBoard.getCell(0, 0)));
        //Now we check and build a dome where we can
        assertTrue(myTurn.canBuildDomeIn(mySpiedPlayer.getWorkers()[0], myBoard.getCell(1, 0)));
        myTurn.buildDomeIn(mySpiedPlayer.getWorkers()[0], myBoard.getCell(1, 0));
        //Check the build happened
        assertTrue(myBoard.getCell(1, 0).getTower().isComplete());
        assertEquals(myTurn.getPerformedAction().get(1).getWorker(), mySpiedPlayer.getWorkers()[0]);
        assertEquals(1, myTurn.getBuilds().size());
        //Check no more allowed workers
        assertEquals(0, myTurn.getAllowedWorkers().size());
    }
}