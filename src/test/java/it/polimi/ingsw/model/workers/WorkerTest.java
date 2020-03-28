package it.polimi.ingsw.model.workers;

import it.polimi.ingsw.model.Player;
import org.junit.Test;

import static org.junit.Assert.*;

public class WorkerTest {

    @Test
    public void setCell() {
    }

    @Test
    public void getCell() {
    }

    @Test
    public void getPlayer() {
    }

    @Test
    public void getWorkerID() {
        Worker[] workers = new Worker[] {   new Worker(new Player("Pippo"), WorkerID.WORKER1),
                                            new Worker(new Player("Pippo"), WorkerID.WORKER1),
                                            new Worker(new Player("Pluto"), WorkerID.WORKER1),
                                            new Worker(new Player("Pluto"), WorkerID.WORKER2),
                                            new Worker(new Player(null), null),
                                            new Worker(null,null)
                                        };
        assertEquals(workers[0], workers[1]);
        assertNotEquals(workers[0], workers[2]);
        assertNotEquals(workers[0], workers[3]);
        assertNotEquals(workers[0], workers[4]);
        assertNotEquals(workers[0], workers[5]);
        assertNotEquals(workers[2], workers[3]);
        assertNotEquals(workers[2], workers[4]);
        assertNotEquals(workers[2], workers[5]);
    }

    @Test
    public void testEquals() {
    }

    @Test
    public void testHashCode() {
    }
}