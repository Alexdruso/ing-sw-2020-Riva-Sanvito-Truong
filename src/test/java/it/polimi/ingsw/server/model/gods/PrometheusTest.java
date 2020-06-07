package it.polimi.ingsw.server.model.gods;

import it.polimi.ingsw.server.model.board.Cell;
import it.polimi.ingsw.server.model.board.Component;
import it.polimi.ingsw.server.model.turnstates.AbstractTurnState;
import it.polimi.ingsw.server.model.turnstates.TurnState;
import it.polimi.ingsw.server.model.workers.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class PrometheusTest {
    private GodsTestHarness testHarness;

    @BeforeEach
    void initGodTestHarness() {
        testHarness = new GodsTestHarness(GodCard.PROMETHEUS.getGod());
    }

    @Test
    void allowBuildAtTurnStart() {
        testHarness.setCell(0, 0, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);

        testHarness.commitState();

        testHarness.getTurnEventsManager().processTurnStartEvents(testHarness.getTurn());

        verify(
                testHarness.getTurn()
        ).setNextState(TurnState.BUILD.getTurnState());
    }

    @Test
    void allowSkipFirstBuild() {
        testHarness.setCell(0, 0, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);

        testHarness.commitState();

        testHarness.getTurnEventsManager().processBeforeBuildEvents(testHarness.getTurn());

        verify(
                testHarness.getTurn()
        ).setSkippable(true);
    }

    @Test
    void goToMoveIfBuiltAfterStart() {
        testHarness.setCell(0, 0, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);

        testHarness.addBuildAction(
                1, 1,
                Component.BLOCK.getInstance(),
                0,
                0
        );

        testHarness.commitState();

        testHarness.getTurnEventsManager().processBeforeBuildEvents(testHarness.getTurn());
        testHarness.getTurnEventsManager().processAfterBuildEvents(testHarness.getTurn());

        verify(
                testHarness.getTurn()
        ).setNextState(TurnState.MOVE.getTurnState());
    }

    @Test
    void doNotChangeMoveIfNotBuilt() {
        testHarness.setCell(0, 0, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);
        testHarness.setCell(1, 1, false, 2);

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
    void forceWorkerOfMoveIfBuilt() {
        testHarness.setCell(0, 0, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);
        testHarness.setCell(2, 2, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);
        testHarness.setCell(1, 1, false, 2);

        testHarness.addBuildAction(
                1, 1,
                Component.BLOCK.getInstance(),
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
    void doNotAllowMoveHigherIfBuilt() {
        testHarness.setCell(1, 1, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);
        testHarness.setCell(0, 1, false, 1);
        testHarness.setCell(0, 0, false, 2);
        testHarness.setCell(2, 0, true, 2);
        testHarness.setCell(2, 1, true, 3);

        testHarness.addBuildAction(
                0, 0,
                Component.BLOCK.getInstance(),
                0,
                0
        );

        testHarness.commitState();

        // Sanity check
        assertTrue(
                testHarness.getWalkableTargetCells(0).getPosition(0, 0)
        );
        assertTrue(
                testHarness.getWalkableTargetCells(0).getPosition(0, 1)
        );
        assertTrue(
                testHarness.getWalkableTargetCells(0).getPosition(1, 0)
        );
        assertFalse(
                testHarness.getWalkableTargetCells(0).getPosition(2, 0)
        );
        assertFalse(
                testHarness.getWalkableTargetCells(0).getPosition(2, 1)
        );
        // End Sanity check

        testHarness.getTurnEventsManager().processBeforeMovementEvents(testHarness.getTurn());

        assertFalse(
                testHarness.getWalkableTargetCells(0).getPosition(0, 0)
        );
        assertTrue(
                testHarness.getWalkableTargetCells(0).getPosition(0, 1)
        );
        assertTrue(
                testHarness.getWalkableTargetCells(0).getPosition(1, 0)
        );
        assertFalse(
                testHarness.getWalkableTargetCells(0).getPosition(2, 0)
        );
        assertFalse(
                testHarness.getWalkableTargetCells(0).getPosition(2, 1)
        );
    }

    @Test
    void doNotChangeBuildAfterBuildAndMove() {
        testHarness.setCell(0, 0, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);
        testHarness.setCell(1, 1,  false, 1);

        testHarness.addBuildAction(
                1, 1,
                Component.BLOCK.getInstance(),
                0,
                0
        );
        testHarness.addMoveAction(
                0, 0,
                1, 1,
                1,
                1,
                0
        );

        testHarness.commitState();

        testHarness.getTurnEventsManager().processAfterBuildEvents(testHarness.getTurn());

        verify(
                testHarness.getTurn(),
                never()
        ).setNextState(any(AbstractTurnState.class));
    }

    @Test
    void doNotChangeBuildAfterOnlyMove() {
        testHarness.setCell(0, 0, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);

        testHarness.addMoveAction(
                0, 0,
                1, 1,
                1,
                1,
                0
        );

        testHarness.commitState();

        testHarness.getTurnEventsManager().processAfterBuildEvents(testHarness.getTurn());

        verify(
                testHarness.getTurn(),
                never()
        ).setNextState(any(AbstractTurnState.class));
    }
}
