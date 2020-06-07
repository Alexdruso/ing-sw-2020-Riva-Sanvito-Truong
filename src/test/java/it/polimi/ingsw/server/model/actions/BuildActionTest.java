package it.polimi.ingsw.server.model.actions;

import it.polimi.ingsw.server.model.board.Buildable;
import it.polimi.ingsw.server.model.board.Cell;
import it.polimi.ingsw.server.model.workers.Worker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class BuildActionTest {
    private final Cell cell = mock(Cell.class);
    private final Buildable buildable = mock(Buildable.class);
    private final int builtLevel = 123;
    private final Worker worker = mock(Worker.class);
    private final BuildAction buildAction = new BuildAction(cell, buildable, builtLevel, worker);

    @Test
    void getTargetCell() {
        assertEquals(buildAction.getTargetCell(), cell);
    }

    @Test
    void getComponent() {
        assertEquals(buildAction.getComponent(), buildable);
    }

    @Test
    void getBuiltLevel() {
        assertEquals(buildAction.getBuiltLevel(), builtLevel);
    }

    @Test
    void getPerformer() {
        assertEquals(buildAction.getWorker(), worker);
    }
}