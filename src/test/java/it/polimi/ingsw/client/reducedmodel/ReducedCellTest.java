package it.polimi.ingsw.client.reducedmodel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ReducedCellTest {
    private final ReducedCell cell = new ReducedCell(2, 3);

    @Test
    void testCoordinates() {
        assertEquals(2, cell.getX());
        assertEquals(3, cell.getY());
    }

    @Test
    void testTowerHeight() {
        assertEquals(0, cell.getTowerHeight());
        cell.setTowerHeight(2);
        assertEquals(2, cell.getTowerHeight());
    }

    @Test
    void testDome() {
        assertFalse(cell.hasDome());
        cell.setHasDome(true);
        assertTrue(cell.hasDome());
    }

    @Test
    void testHighlighted() {
        assertFalse(cell.isHighlighted());
        cell.setHighlighted(true);
        assertTrue(cell.isHighlighted());
    }

    @Test
    void testWorker() {
        assertTrue(cell.getWorker().isEmpty());
        ReducedWorker mockWorker = mock(ReducedWorker.class);
        cell.setWorker(mockWorker);
        assertTrue(cell.getWorker().isPresent());
        cell.getWorker().ifPresent(result -> assertEquals(mockWorker, result));
        cell.setNoWorker();
        assertTrue(cell.getWorker().isEmpty());
    }
}