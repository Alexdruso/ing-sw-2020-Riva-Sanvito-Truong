package it.polimi.ingsw.model.actions;

import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.workers.Worker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class MoveActionTest {
    private final Cell sourceCell = mock(Cell.class);
    private final Cell targetCell = mock(Cell.class);
    private final int sourceLevel = 123;
    private final int targetLevel = 456;
    private final Worker worker = mock(Worker.class);
    private final MoveAction moveAction = new MoveAction(sourceCell, targetCell, sourceLevel, targetLevel, worker);

    @Test
    void getSourceCell() {
        assertEquals(moveAction.getSourceCell(), sourceCell);
    }

    @Test
    void getTargetCell() {
        assertEquals(moveAction.getTargetCell(), targetCell);
    }

    @Test
    void getSourceLevel() {
        assertEquals(moveAction.getSourceLevel(), sourceLevel);
    }

    @Test
    void getTargetLevel() {
        assertEquals(moveAction.getTargetLevel(), targetLevel);
    }

    @Test
    void getPerformer() {
        assertEquals(moveAction.getPerformer(), worker);
    }
}