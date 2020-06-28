package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.utils.networking.transmittables.ReducedGod;
import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;
import it.polimi.ingsw.utils.networking.transmittables.ReducedWorkerID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReducedPlayerTest {
    private final String mockNickname = "MyMockNick";
    private final ReducedUser mockUser = mock(ReducedUser.class);
    private final ReducedPlayer player = new ReducedPlayer(mockUser, true, 2);

    @BeforeEach
    void setupMocks() {
        when(mockUser.getNickname()).thenReturn(mockNickname);
    }

    @Test
    void testGetters() {
        assertEquals(mockUser, player.getUser());
        assertEquals(mockNickname, player.getNickname());
        assertTrue(player.isLocalPlayer());
        assertEquals(2, player.getPlayerIndex());
    }

    @Test
    void testInGame() {
        assertTrue(player.isInGame());
        player.setInGame(false);
        assertFalse(player.isInGame());
    }

    @Test
    void testGod() {
        ReducedGod mockGod = new ReducedGod("MyGod");
        player.setGod(mockGod);
        assertEquals("MyGod", player.getGod().getName());
    }

    @Test
    void testWorkers() {
        ReducedWorker worker = new ReducedWorker(ReducedWorkerID.WORKER1, player);
        player.addWorker(worker);
        assertEquals(worker, player.getWorker(ReducedWorkerID.WORKER1));
        assertEquals(1, player.getWorkers().size());
        assertEquals(worker, player.getWorkers().get(ReducedWorkerID.WORKER1));
        player.removeWorker(ReducedWorkerID.WORKER1);
        assertNull(player.getWorker(ReducedWorkerID.WORKER1));
    }

}