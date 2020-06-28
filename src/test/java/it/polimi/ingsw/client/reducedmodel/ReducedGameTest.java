package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.utils.networking.transmittables.ReducedComponent;
import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;
import it.polimi.ingsw.utils.networking.transmittables.ReducedWorkerID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ReducedGameTest {
    private final String nickname = "MyNick";
    private final ReducedUser user = new ReducedUser(nickname);
    private final ReducedPlayer player = new ReducedPlayer(user, true, 0);
    private final List<ReducedPlayer> players = new ArrayList<>();
    private ReducedGame game;

    @BeforeEach
    void setupMocks() {
        players.add(player);
        game = new ReducedGame(players);
    }

    @Test
    void testBoard() {
        assertNotNull(game.getBoard());
    }

    @Test
    void testPlayers() {
        assertEquals(players.size(), game.getPlayersList().size());
        assertEquals(players.size(), game.getPlayersCount());
        assertTrue(players.containsAll(game.getPlayersList()));
        assertTrue(game.getPlayer(nickname).isPresent());
        game.getPlayer(nickname).ifPresent(result -> assertEquals(player, result));
        assertTrue(game.getPlayer(user).isPresent());
        game.getPlayer(user).ifPresent(result -> assertEquals(player, result));
    }

    @Test
    void testTurn() {
        ReducedTurn mockTurn = mock(ReducedTurn.class);
        game.setTurn(mockTurn);
        assertEquals(mockTurn, game.getTurn());
    }

    @Test
    void testWorker() {
        game.addWorker(user, ReducedWorkerID.WORKER1, 2, 3);
        assertEquals(2, player.getWorker(ReducedWorkerID.WORKER1).getCell().getX());
        assertEquals(3, player.getWorker(ReducedWorkerID.WORKER1).getCell().getY());

        game.setWorkerCell(user, ReducedWorkerID.WORKER1, 2, 3, 0, 4);
        assertEquals(0, player.getWorker(ReducedWorkerID.WORKER1).getCell().getX());
        assertEquals(4, player.getWorker(ReducedWorkerID.WORKER1).getCell().getY());

        game.removeWorker(user, ReducedWorkerID.WORKER1, 0, 4);
        assertNull(player.getWorker(ReducedWorkerID.WORKER1));
    }

    @Test
    void testBuild() {
        game.buildComponentInCell(2, 4, ReducedComponent.BLOCK, 1);
        assertEquals(1, game.getBoard().getCell(2, 4).getTowerHeight());
        assertFalse(game.getBoard().getCell(2, 4).hasDome());

        game.buildComponentInCell(0, 1, ReducedComponent.DOME, 2);
        assertEquals(2, game.getBoard().getCell(0, 1).getTowerHeight());
        assertTrue(game.getBoard().getCell(0, 1).hasDome());
    }

}