package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utils.networking.transmittables.ReducedWorkerID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ReducedWorkerTest {
    private final ReducedPlayer mockPlayer = mock(ReducedPlayer.class);
    private final ReducedWorker worker = new ReducedWorker(ReducedWorkerID.WORKER1, mockPlayer);

    @Test
    void testGetters() {
        assertEquals(ReducedWorkerID.WORKER1, worker.getWorkerID());
        assertEquals(mockPlayer, worker.getPlayer());
    }

    @Test
    void testCell() {
        ReducedCell mockCell = mock(ReducedCell.class);
        worker.setCell(mockCell);
        assertEquals(mockCell, worker.getCell());
    }

}