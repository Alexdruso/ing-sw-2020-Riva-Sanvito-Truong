package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.model.board.Component;
import it.polimi.ingsw.model.workers.WorkerID;
import it.polimi.ingsw.utils.messages.*;
import it.polimi.ingsw.view.View;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameTest {

    @Test
    public void gameTest() {
        //setup the users
        User[] users = {
                spy(new User("Peter Parker")),
                spy(new User("Tony Stark")),
                spy(new User("Steve Rogers"))
        };
        //create a mock view
        View myView = mock(View.class);
        //setup the game
        Game myGame = new Game(3);
        myGame.addObserver(myView, (obs, message) ->
                ((View) obs).updateFromGame(message));
        for (User user : users) myGame.subscribeUser(user);
        //now start a game session
        myGame.setup();
        assertTrue(myGame.isActive());
        verify(myView).updateFromGame(any(ServerStartSetupMatchMessage.class));
        verify(myView).updateFromGame(any(ServerAskGodsFromListMessage.class));
        //casual test of right state
        assertFalse(
                myGame.isValidMove(
                        0,
                        0,
                        1,
                        1,
                        WorkerID.WORKER1.toReducedWorkerId(),
                        users[0]
                )
        );
        //now test the choose gods part
        assertTrue(
                myGame.isValidGodsChoice(
                        Arrays.asList(
                                new ReducedGod("Apollo"),
                                new ReducedGod("Athena"),
                                new ReducedGod("Demeter")
                        ),
                        users[0]
                )
        );
        assertFalse(
                myGame.isValidGodsChoice(
                        Arrays.asList(
                                new ReducedGod("Apollo"),
                                new ReducedGod("Athena"),
                                new ReducedGod("Demeter")
                        ),
                        users[1]
                )
        );
        assertFalse(
                myGame.isValidGodsChoice(
                        Arrays.asList(
                                new ReducedGod("Apollo"),
                                new ReducedGod("Athena")
                        ),
                        users[0]
                )
        );
        assertFalse(
                myGame.isValidGodsChoice(
                        Arrays.asList(
                                new ReducedGod("Apollo"),
                                new ReducedGod("Athena"),
                                new ReducedGod("Flying Spaghetti Monster") //so unfair he's not in game :^)
                        ),
                        users[1]
                )
        );
        //Pick a gods list
        myGame.setAvailableGodsList(
                Arrays.asList(
                        new ReducedGod("Apollo"),
                        new ReducedGod("Athena"),
                        new ReducedGod("Demeter")
                )
        );
        verify(myView).updateFromGame(any(ServerAskGodFromListMessage.class));
        //now test the choose god part
        assertTrue(
                myGame.isValidGodChoice(
                        new ReducedGod("APOLLO"),
                        users[1]
                )
        );
        assertFalse(
                myGame.isValidGodChoice(
                        new ReducedGod("Flying Spaghetti Monster"),
                        users[1]
                )
        );
        assertFalse(
                myGame.isValidGodChoice(
                        new ReducedGod("apollo"),
                        users[0]
                )
        );
        //pick a god
        myGame.setGod(
                new ReducedGod("APOLLO"),
                users[1]
        );
        verify(myView).updateFromGame(any(ServerSetGodMessage.class));
        verify(myView, times(2)).updateFromGame(any(ServerAskGodFromListMessage.class));
        //now test again in choose god state
        assertTrue(
                myGame.isValidGodChoice(
                        new ReducedGod("ATHENA"),
                        users[2]
                )
        );
        assertFalse(
                myGame.isValidGodChoice(
                        new ReducedGod("APOLLO"),
                        users[2]
                )
        );
        assertFalse(
                myGame.isValidGodChoice(
                        new ReducedGod("Flying Spaghetti Monster"),
                        users[2]
                )
        );
        assertFalse(
                myGame.isValidGodChoice(
                        new ReducedGod("ATHENA"),
                        users[0]
                )
        );
        //pick a god
        myGame.setGod(
                new ReducedGod("aTheNa"),
                users[2]
        );
        verify(myView, times(3)).updateFromGame(any(ServerSetGodMessage.class));
        verify(myView, times(2)).updateFromGame(any(ServerAskGodFromListMessage.class));
        verify(myView).updateFromGame(any(ServerAskStartPlayerMessage.class));
        //test the choose start player part
        assertTrue(
                myGame.isValidStartPlayerChoice(
                        users[0].toReducedUser(),
                        users[0]
                )
        );
        assertTrue(
                myGame.isValidStartPlayerChoice(
                        users[2].toReducedUser(),
                        users[0]
                )
        );
        assertFalse(
                myGame.isValidStartPlayerChoice(
                        users[2].toReducedUser(),
                        users[1]
                )
        );
        assertFalse(
                myGame.isValidStartPlayerChoice(
                        new ReducedUser("Paperino"),
                        users[0]
                )
        );
        //set a player
        myGame.setStartPlayer(
                users[1].toReducedUser()
        );
        verify(myView).updateFromGame(any(ServerAskWorkerPositionMessage.class));
        //setWorkerPosition state
        assertTrue(
                myGame.isValidPositioning(
                        0,
                        0,
                        WorkerID.WORKER1.toReducedWorkerId(),
                        users[1]
                )
        );
        assertFalse(
                myGame.isValidPositioning(
                        25,
                        0,
                        WorkerID.WORKER1.toReducedWorkerId(),
                        users[1]
                )
        );
        assertFalse(
                myGame.isValidPositioning(
                        25,
                        0,
                        WorkerID.WORKER1.toReducedWorkerId(),
                        users[1]
                )
        );
        assertFalse(
                myGame.isValidPositioning(
                        0,
                        25,
                        WorkerID.WORKER1.toReducedWorkerId(),
                        users[1]
                )
        );
        assertFalse(
                myGame.isValidPositioning(
                        0,
                        0,
                        WorkerID.WORKER1.toReducedWorkerId(),
                        users[2]
                )
        );
        //pick a position
        myGame.setWorkerPosition(0, 0, WorkerID.WORKER1.toReducedWorkerId(), users[1]);
        verify(myView).updateFromGame(any(ServerSetWorkerStartPositionMessage.class));
        verify(myView, times(2)).updateFromGame(any(ServerAskWorkerPositionMessage.class));
        //setWorkerPosition state
        assertTrue(
                myGame.isValidPositioning(
                        1,
                        1,
                        WorkerID.WORKER2.toReducedWorkerId(),
                        users[1]
                )
        );
        assertFalse(
                myGame.isValidPositioning(
                        0,
                        0,
                        WorkerID.WORKER2.toReducedWorkerId(),
                        users[1]
                )
        );
        assertFalse(
                myGame.isValidPositioning(
                        25,
                        0,
                        WorkerID.WORKER1.toReducedWorkerId(),
                        users[1]
                )
        );
        assertFalse(
                myGame.isValidPositioning(
                        25,
                        0,
                        WorkerID.WORKER2.toReducedWorkerId(),
                        users[1]
                )
        );
        assertFalse(
                myGame.isValidPositioning(
                        0,
                        25,
                        WorkerID.WORKER2.toReducedWorkerId(),
                        users[1]
                )
        );
        assertFalse(
                myGame.isValidPositioning(
                        0,
                        0,
                        WorkerID.WORKER2.toReducedWorkerId(),
                        users[2]
                )
        );
        //pick a position
        myGame.setWorkerPosition(1, 1, WorkerID.WORKER2.toReducedWorkerId(), users[1]);
        verify(myView, times(2)).updateFromGame(any(ServerSetWorkerStartPositionMessage.class));
        verify(myView, times(3)).updateFromGame(any(ServerAskWorkerPositionMessage.class));
        //verify set worker position
        assertTrue(
                myGame.isValidPositioning(
                        2,
                        2,
                        WorkerID.WORKER1.toReducedWorkerId(),
                        users[2]
                )
        );
        assertFalse(
                myGame.isValidPositioning(
                        0,
                        0,
                        WorkerID.WORKER1.toReducedWorkerId(),
                        users[2]
                )
        );
        assertFalse(
                myGame.isValidPositioning(
                        2,
                        2,
                        WorkerID.WORKER1.toReducedWorkerId(),
                        users[1]
                )
        );
        //pick a position
        myGame.setWorkerPosition(2, 2, WorkerID.WORKER1.toReducedWorkerId(), users[2]);
        verify(myView, times(3)).updateFromGame(any(ServerSetWorkerStartPositionMessage.class));
        verify(myView, times(4)).updateFromGame(any(ServerAskWorkerPositionMessage.class));
        //verify set worker position
        assertTrue(
                myGame.isValidPositioning(
                        3,
                        3,
                        WorkerID.WORKER2.toReducedWorkerId(),
                        users[2]
                )
        );
        //pick a position
        myGame.setWorkerPosition(3, 3, WorkerID.WORKER2.toReducedWorkerId(), users[2]);
        verify(myView, times(4)).updateFromGame(any(ServerSetWorkerStartPositionMessage.class));
        verify(myView, times(5)).updateFromGame(any(ServerAskWorkerPositionMessage.class));
        //verify set worker position
        assertTrue(
                myGame.isValidPositioning(
                        4,
                        4,
                        WorkerID.WORKER1.toReducedWorkerId(),
                        users[0]
                )
        );
        //pick a position
        myGame.setWorkerPosition(4, 4, WorkerID.WORKER1.toReducedWorkerId(), users[0]);
        verify(myView, times(5)).updateFromGame(any(ServerSetWorkerStartPositionMessage.class));
        verify(myView, times(6)).updateFromGame(any(ServerAskWorkerPositionMessage.class));
        //verify set worker position
        assertTrue(
                myGame.isValidPositioning(
                        0,
                        1,
                        WorkerID.WORKER2.toReducedWorkerId(),
                        users[0]
                )
        );
        //pick a position
        myGame.setWorkerPosition(0, 1, WorkerID.WORKER2.toReducedWorkerId(), users[0]);
        verify(myView, times(6)).updateFromGame(any(ServerSetWorkerStartPositionMessage.class));
        verify(myView, times(6)).updateFromGame(any(ServerAskWorkerPositionMessage.class));
        verify(myView).updateFromGame(any(ServerAskMoveMessage.class));
        //now we are in a ask move state
        assertFalse(
                myGame.isValidSkip(users[0])
        );
        assertTrue(
                myGame.isValidMove(
                        0,
                        0,
                        1,
                        0,
                        WorkerID.WORKER1.toReducedWorkerId(),
                        users[1]
                )
        );
        assertTrue(
                myGame.isValidMove(
                        1,
                        1,
                        1,
                        2,
                        WorkerID.WORKER2.toReducedWorkerId(),
                        users[1]
                )
        );
        assertFalse(
                myGame.isValidMove(
                        1,
                        0,
                        1,
                        0,
                        WorkerID.WORKER1.toReducedWorkerId(),
                        users[1]
                )
        );
        assertFalse(
                myGame.isValidMove(
                        25,
                        0,
                        1,
                        0,
                        WorkerID.WORKER1.toReducedWorkerId(),
                        users[1]
                )
        );
        assertFalse(
                myGame.isValidMove(
                        0,
                        0,
                        3,
                        3,
                        WorkerID.WORKER1.toReducedWorkerId(),
                        users[1]
                )
        );
        assertFalse(
                myGame.isValidMove(
                        0,
                        0,
                        1,
                        0,
                        WorkerID.WORKER2.toReducedWorkerId(),
                        users[1]
                )
        );
        assertFalse(
                myGame.isValidMove(
                        0,
                        0,
                        1,
                        0,
                        WorkerID.WORKER1.toReducedWorkerId(),
                        users[2]
                )
        );
        //make the move
        myGame.move(1, 0, WorkerID.WORKER1.toReducedWorkerId(), users[1]);
        verify(myView).updateFromGame(any(ServerMoveMessage.class));
        verify(myView).updateFromGame(any(ServerAskBuildMessage.class));
        //now build state
        assertTrue(
                myGame.isValidBuild(
                        0,
                        0,
                        Component.BLOCK.toReducedComponent(),
                        WorkerID.WORKER1.toReducedWorkerId(),
                        users[1]
                )
        );
        assertFalse(
                myGame.isValidBuild(
                        0,
                        0,
                        Component.DOME.toReducedComponent(),
                        WorkerID.WORKER1.toReducedWorkerId(),
                        users[1]
                )
        );
        assertFalse(
                myGame.isValidBuild(
                        2,
                        1,
                        Component.DOME.toReducedComponent(),
                        WorkerID.WORKER2.toReducedWorkerId(),
                        users[1]
                )
        );
        assertFalse(
                myGame.isValidBuild(
                        0,
                        0,
                        Component.DOME.toReducedComponent(),
                        WorkerID.WORKER1.toReducedWorkerId(),
                        users[0]
                )
        );
        //make the build
        myGame.build(0, 0, Component.BLOCK.toReducedComponent(), WorkerID.WORKER1.toReducedWorkerId(), users[1]);
        verify(myView).updateFromGame(any(ServerBuildMessage.class));
        verify(myView, times(2)).updateFromGame(any(ServerAskMoveMessage.class));
        //now one turn each
        assertTrue(
                myGame.isValidMove(
                        2,
                        2,
                        2,
                        1,
                        WorkerID.WORKER1.toReducedWorkerId(),
                        users[2]
                )
        );
        myGame.move(2, 1, WorkerID.WORKER1.toReducedWorkerId(), users[2]);
        assertTrue(
                myGame.isValidBuild(
                        2,
                        2,
                        Component.BLOCK.toReducedComponent(),
                        WorkerID.WORKER1.toReducedWorkerId(),
                        users[2]
                )
        );
        myGame.build(2, 2, Component.BLOCK.toReducedComponent(), WorkerID.WORKER1.toReducedWorkerId(), users[2]);
        assertTrue(
                myGame.isValidMove(
                        4,
                        4,
                        4,
                        3,
                        WorkerID.WORKER1.toReducedWorkerId(),
                        users[0]
                )
        );
        myGame.move(4, 3, WorkerID.WORKER1.toReducedWorkerId(), users[0]);
        assertTrue(
                myGame.isValidBuild(
                        4,
                        4,
                        Component.BLOCK.toReducedComponent(),
                        WorkerID.WORKER1.toReducedWorkerId(),
                        users[0]
                )
        );
        myGame.build(4, 4, Component.BLOCK.toReducedComponent(), WorkerID.WORKER1.toReducedWorkerId(), users[0]);
        assertTrue(
                myGame.isValidSkip(users[0])
        );
        myGame.skip();
        //trigger losing turn on user[1]
        myGame.triggerLosingTurn();
        verify(myView).updateFromGame(any(ServerLoseGameMessage.class));
        verify(myView, times(2)).updateFromGame(any(ServerRemoveWorkerMessage.class));
        assertFalse(
                myGame.isInGame(users[1])
        );
        //trigger another winning turn
        myGame.triggerLosingTurn();
        verify(myView, times(2)).updateFromGame(any(ServerLoseGameMessage.class));
        verify(myView, times(4)).updateFromGame(any(ServerRemoveWorkerMessage.class));
        verify(myView).updateFromGame(any(ServerWinGameMessage.class));
        assertFalse(
                myGame.isInGame(users[2])
        );
        assertEquals(2, myGame.getLastRoundTurnsList().size());
        //a player draws
        myGame.draw();
        assertFalse(myGame.isActive());
        //press f for user[0]
        myGame.unsubscribeUser(users[0]);
        assertFalse(
                myGame.isInGame(users[0])
        );
    }


    @Test
    public void cannotRegisterMoreUsersThanDeclared() {
        Game game = new Game(3);
        game.subscribeUser(new User("Romano Prodi"));
        game.subscribeUser(new User("Sghiribizou"));
        game.subscribeUser(new User("Nick Name"));
        assertThrows(IllegalStateException.class,
                () -> game.subscribeUser(new User("Luccio")));
        Game game2 = new Game(2);
        game2.subscribeUser(new User("Nagito Komaeda"));
        game2.subscribeUser(new User("Francesco Totti"));
        assertThrows(IllegalStateException.class,
                () -> game.subscribeUser(new User("Genoveffo Brombeis")));
    }

    @Test
    public void testRegisterPlayers() {
        Game game = new Game(3);
        game.subscribeUser(new User("Maria Ortigli"));
        game.subscribeUser(new User("Buggo"));
        game.subscribeUser(new User("Zeb89"));
        List<Player> playerList = game.getPlayersList();
        assertEquals(playerList.get(0).getNickname(), "Maria Ortigli");
        assertEquals(playerList.get(1).getNickname(), "Buggo");
        assertEquals(playerList.get(2).getNickname(), "Zeb89");
    }
}
