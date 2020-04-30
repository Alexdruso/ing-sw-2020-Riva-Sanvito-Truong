package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.turnstates.AbstractTurnState;
import it.polimi.ingsw.model.turnstates.TurnState;
import it.polimi.ingsw.model.workers.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ArtemisTest {
    private GodsTestHarness testHarness;

    @BeforeEach
    void initGodTestHarness() {
        testHarness = new GodsTestHarness(GodCard.ARTEMIS.getGod());
    }

    @Test
    void doNotChangeFirstMove() {
        testHarness.setCell(0, 0, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);

        testHarness.commitState();

        List<Cell> previouslyWalkableCells = testHarness.getGame().getBoard().getTargets(
                testHarness.getWalkableTargetCells(0)
        );

        testHarness.getTurnEventsManager().processBeforeMovementEvents(testHarness.getTurn());

        List<Cell> laterWalkableCells = testHarness.getGame().getBoard().getTargets(
                testHarness.getWalkableTargetCells(0)
        );

        verify(
                testHarness.getTurn(),
                never()
        ).setSkippable(true);
        verify(
                testHarness.getTurn(),
                never()
        ).clearAllowedWorkers();
        verify(
                testHarness.getTurn(),
                never()
        ).addAllowedWorker(any(Worker.class));
        assertTrue(
                previouslyWalkableCells.containsAll(laterWalkableCells)
        );
        assertTrue(
                previouslyWalkableCells.size() == laterWalkableCells.size()
        );
    }

    @Test
    void allowSkipSecondMove() {
        testHarness.setCell(0, 0, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);

        testHarness.addMoveAction(
                1, 1,
                0, 0,
                1,
                1,
                0
        );

        testHarness.commitState();

        testHarness.getTurnEventsManager().processBeforeMovementEvents(testHarness.getTurn());

        verify(
                testHarness.getTurn()
        ).setSkippable(true);
    }

    @Test
    void forceWorkerOfSecondMove() {
        testHarness.setCell(0, 0, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);
        testHarness.setCell(2, 2, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);

        testHarness.addMoveAction(
                1, 1,
                2, 2,
                1,
                1,
                1
        );

        testHarness.commitState();

        testHarness.getTurnEventsManager().processBeforeMovementEvents(testHarness.getTurn());

        verify(
                testHarness.getTurn()
        ).clearAllowedWorkers();
        verify(
                testHarness.getTurn()
        ).addAllowedWorker(testHarness.getWorker(1));
    }

    @Test
    void doNotAllowMoveToPreviousCell() {
        testHarness.setCell(0, 0, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);

        testHarness.addMoveAction(
                1, 1,
                0, 0,
                1,
                1,
                0
        );

        testHarness.commitState();

        testHarness.getTurnEventsManager().processBeforeMovementEvents(testHarness.getTurn());

        assertFalse(
                testHarness.getWalkableTargetCells(0).getPosition(1, 1)
        );
    }

    @Test
    void doNotChangeAfterSecondMove() {
        testHarness.setCell(0, 0, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);

        testHarness.addMoveAction(
                2, 2,
                1, 1,
                1,
                1,
                0
        );
        testHarness.addMoveAction(
                1, 1,
                0, 0,
                1,
                1,
                0
        );

        testHarness.commitState();

        testHarness.getTurnEventsManager().processAfterMovementEvents(testHarness.getTurn());

        verify(
                testHarness.getTurn(),
                never()
        ).setNextState(any(AbstractTurnState.class));
    }

    @Test
    void allowSecondMoveAfterFirst() {
        testHarness.setCell(0, 0, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);

        testHarness.addMoveAction(
                1, 1,
                0, 0,
                1,
                1,
                0
        );

        testHarness.commitState();

        testHarness.getTurnEventsManager().processAfterMovementEvents(testHarness.getTurn());

        verify(
                testHarness.getTurn()
        ).setNextState(TurnState.MOVE.getTurnState());
    }
}
