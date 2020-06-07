package it.polimi.ingsw.server.model.workers;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.board.Cell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class WorkerTest {

    @Test
    public void setAndGetCell() {
        Player owner = new Player("Pippo");
        Worker testWorker = new Worker(owner, WorkerID.WORKER1);
        Cell testCell = new Cell(1, 1);
        testWorker.setCell(testCell);
        assertEquals(testCell, testWorker.getCell());
    }

    @Test
    public void getPlayer() {
        Player owner = new Player("Pippo");
        Worker testWorker = new Worker(owner, WorkerID.WORKER1);
        assertEquals(owner, testWorker.getPlayer());
    }

    @Test
    public void getWorkerID() {
        Player owner = new Player("Pippo");
        Worker testWorker = new Worker(owner, WorkerID.WORKER1);
        assertEquals(WorkerID.WORKER1, testWorker.getWorkerID());
        testWorker = new Worker(owner, WorkerID.WORKER2);
        assertEquals(WorkerID.WORKER2, testWorker.getWorkerID());
    }

    @Test
    public void testEquals() {
        Player player = new Player("Pippo");
        Worker[] workers = new Worker[]{
                new Worker(player, WorkerID.WORKER1),
                new Worker(new Player("Pippo"), WorkerID.WORKER1),
                new Worker(new Player("Pluto"), WorkerID.WORKER1),
                new Worker(new Player("Pluto"), WorkerID.WORKER2),
                new Worker(new Player(null), null),
                new Worker(null, null),
                new Worker(player, WorkerID.WORKER1),
                new Worker(player, WorkerID.WORKER2)
        };
        assertEquals(workers[0], workers[6]);
        assertNotEquals(workers[0], workers[7]);
        assertNotEquals(workers[0], workers[1]);
        assertNotEquals(workers[0], workers[2]);
        assertNotEquals(workers[0], workers[3]);
        assertNotEquals(workers[0], workers[4]);
        assertNotEquals(workers[0], workers[5]);
        assertNotEquals(workers[2], workers[3]);
        assertNotEquals(workers[2], workers[4]);
        assertNotEquals(workers[2], workers[5]);
    }

    @Test
    public void testHashCode() {
        Worker[] workers = new Worker[]{
                new Worker(new Player("Pippo"), WorkerID.WORKER1),
                new Worker(new Player("Pippo"), WorkerID.WORKER1),
                new Worker(new Player("Pluto"), WorkerID.WORKER1),
                new Worker(new Player("Pluto"), WorkerID.WORKER2),
                new Worker(new Player(null), null),
                new Worker(null, null)
        };
        assertNotEquals(workers[0].hashCode(), workers[1].hashCode());
        assertNotEquals(workers[0].hashCode(), workers[2].hashCode());
        assertNotEquals(workers[0].hashCode(), workers[3].hashCode());
        assertNotEquals(workers[0].hashCode(), workers[4].hashCode());
        assertNotEquals(workers[0].hashCode(), workers[5].hashCode());
        assertNotEquals(workers[2].hashCode(), workers[3].hashCode());
        assertNotEquals(workers[2].hashCode(), workers[4].hashCode());
        assertNotEquals(workers[2].hashCode(), workers[5].hashCode());
    }
}