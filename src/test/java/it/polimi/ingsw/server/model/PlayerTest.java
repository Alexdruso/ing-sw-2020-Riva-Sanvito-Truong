package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.gods.God;
import it.polimi.ingsw.server.model.turnevents.TurnEventsManager;
import it.polimi.ingsw.server.model.workers.Worker;
import it.polimi.ingsw.server.model.workers.WorkerID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlayerTest {
    God godMock;

    @BeforeEach
    void godSetup() {
        godMock = mock(God.class);
        when(godMock.getName()).thenReturn("Thor");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Pippo", "Pluto", "Kek", ""})
    void setAndGetNickname(String name) {
        Player testPlayer = new Player(name);
        assertEquals(testPlayer.getNickname(), name);
        testPlayer.setNickname(name);
        assertEquals(testPlayer.getNickname(), name);
    }

    @Test
    void getOwnWorkers() {
        Player testPlayer = new Player("Kek");

        for (int i = 0; i < testPlayer.getWorkers().length; i++) {
            assertEquals(testPlayer.getWorkers()[i].getWorkerID(), WorkerID.values()[i]);
            assertEquals(testPlayer.getWorkers()[i].getPlayer(), testPlayer);
        }

        assertNotSame(testPlayer.getWorkers(), testPlayer.getWorkers());
    }

    @ParameterizedTest
    @EnumSource(WorkerID.class)
    void testWorkerByID(WorkerID id) {
        Player testPlayer = new Player("Marco Bevaldo");
        Worker worker = testPlayer.getWorkerByID(id);
        assertEquals(worker.getWorkerID(), id);
    }

    @Test
    void setAndGetGod() {
        Player testPlayer = new Player("Kek");
        testPlayer.setGod(godMock);
        assertEquals(godMock, testPlayer.getGod());
    }

    @Test
    void getTurnEventsManager() {
        Player testPlayer = new Player("Kek");
        testPlayer.setGod(godMock);

        assertEquals(testPlayer.getTurnEventsManager().getClass(), TurnEventsManager.class);
    }
}