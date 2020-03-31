package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.workers.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApolloTest {
    private GodsTestHarness testHarness;

    @BeforeEach
    void initGodTestHarness() {
        testHarness = new GodsTestHarness(GodCard.APOLLO.getGod());
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
                testHarness.getCell(0, 0)
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
