package it.polimi.ingsw.server.model.gods;

import it.polimi.ingsw.server.model.board.Cell;
import it.polimi.ingsw.server.model.workers.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class MinotaurTest {
    private GodsTestHarness testHarness;

    @BeforeEach
    void initGodTestHarness() {
        testHarness = new GodsTestHarness(GodCard.MINOTAUR.getGod());
    }

    @Test
    void allowMoveToOpponentSameLevel() {
        testHarness.setCell(0, 0, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);
        testHarness.setCell(1, 1, GodsTestHarness.MockedPlayer.OPPONENT1.player, false, 1);

        testHarness.commitState();

        testHarness.getTurnEventsManager().processBeforeMovementEvents(testHarness.getTurn());

        assertTrue(testHarness.getWalkableTargetCells(0).getPosition(1, 1));
    }

    @Test
    void doNotAllowMoveToOpponentOutOfBoard() {
        testHarness.setCell(1, 1, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);
        testHarness.setCell(0, 0, GodsTestHarness.MockedPlayer.OPPONENT1.player, false, 1);

        testHarness.commitState();

        testHarness.getTurnEventsManager().processBeforeMovementEvents(testHarness.getTurn());

        assertFalse(testHarness.getWalkableTargetCells(0).getPosition(0, 0));
    }

    @Test
    void doNotAllowMoveToOpponentTooHigh() {
        testHarness.setCell(0, 0, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);
        testHarness.setCell(1, 1, GodsTestHarness.MockedPlayer.OPPONENT1.player, false, 3);

        testHarness.commitState();

        testHarness.getTurnEventsManager().processBeforeMovementEvents(testHarness.getTurn());

        assertFalse(testHarness.getWalkableTargetCells(0).getPosition(1, 1));
    }

    @Test
    void performMoveToOpponentSameLevel() {
        testHarness.setCell(0, 0, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);
        testHarness.setCell(1, 1, GodsTestHarness.MockedPlayer.OPPONENT1.player, false, 1);

        testHarness.addMoveAction(
                0, 0,
                1, 1,
                1,
                1,
                0
        );

        testHarness.commitState();

        testHarness.getTurnEventsManager().processAfterMovementEvents(testHarness.getTurn());

        verify(
                testHarness.getGame()
        ).setWorkerCell(
                testHarness.getWorker(GodsTestHarness.MockedPlayer.OPPONENT1.player, 0),
                testHarness.getCell(2, 2)
        );
    }

    @Test
    void doNotPerformMoveNoOpponent() {
        testHarness.setCell(0, 0, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);
        testHarness.setCell(1, 0, GodsTestHarness.MockedPlayer.OPPONENT1.player, false, 1);

        testHarness.addMoveAction(
                0, 0,
                1, 1,
                1,
                1,
                0
        );

        testHarness.commitState();

        testHarness.getTurnEventsManager().processAfterMovementEvents(testHarness.getTurn());

        verify(
                testHarness.getGame(),
                never()
        ).setWorkerCell(any(Worker.class), any(Cell.class));
    }
}
