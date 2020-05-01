package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Component;
import it.polimi.ingsw.model.turnstates.AbstractTurnState;
import it.polimi.ingsw.model.turnstates.TurnState;
import it.polimi.ingsw.model.workers.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class HephaestusTest {
    private GodsTestHarness testHarness;

    @BeforeEach
    void initGodTestHarness() {
        testHarness = new GodsTestHarness(GodCard.HEPHAESTUS.getGod());
    }

    @Test
    void doNotChangeFirstBuild() {
        testHarness.setCell(0, 0, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);

        testHarness.commitState();

        List<Cell> previouslyBlockBuildableCells = testHarness.getGame().getBoard().getTargets(
                testHarness.getBlockBuildableTargetCells(0)
        );
        List<Cell> previouslyDomeBuildableCells = testHarness.getGame().getBoard().getTargets(
                testHarness.getDomeBuildableTargetCells(0)
        );

        testHarness.getTurnEventsManager().processBeforeBuildEvents(testHarness.getTurn());

        List<Cell> laterBlockBuildableCells = testHarness.getGame().getBoard().getTargets(
                testHarness.getBlockBuildableTargetCells(0)
        );
        List<Cell> laterDomeBuildableCells = testHarness.getGame().getBoard().getTargets(
                testHarness.getDomeBuildableTargetCells(0)
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
                previouslyBlockBuildableCells.containsAll(laterBlockBuildableCells)
        );
        assertTrue(
                previouslyBlockBuildableCells.size() == laterBlockBuildableCells.size()
        );
        assertTrue(
                previouslyDomeBuildableCells.containsAll(laterDomeBuildableCells)
        );
        assertTrue(
                previouslyDomeBuildableCells.size() == laterDomeBuildableCells.size()
        );
    }

    @Test
    void allowSkipSecondBuild() {
        testHarness.setCell(0, 0, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);
        testHarness.setCell(1, 1, false, 1);

        testHarness.addBuildAction(
                1, 1,
                Component.BLOCK.getInstance(),
                0,
                0
        );

        testHarness.commitState();

        testHarness.getTurnEventsManager().processBeforeBuildEvents(testHarness.getTurn());

        verify(
                testHarness.getTurn()
        ).setSkippable(true);
    }

    @Test
    void forceWorkerOfSecondBuild() {
        testHarness.setCell(0, 0, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);
        testHarness.setCell(2, 2, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);
        testHarness.setCell(1, 1, false, 1);

        testHarness.addBuildAction(
                1, 1,
                Component.BLOCK.getInstance(),
                0,
                1
        );

        testHarness.commitState();

        testHarness.getTurnEventsManager().processBeforeBuildEvents(testHarness.getTurn());

        verify(
                testHarness.getTurn()
        ).clearAllowedWorkers();
        verify(
                testHarness.getTurn()
        ).addAllowedWorker(testHarness.getWorker(1));
    }

    @Test
    void allowBuildBlockOnlyOnPreviousCell() {
        testHarness.setCell(0, 0, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);
        testHarness.setCell(1, 1, false, 1);

        testHarness.addBuildAction(
                1, 1,
                Component.BLOCK.getInstance(),
                0,
                0
        );
        testHarness.commitState();

        // Sanity check
        assertTrue(
                testHarness.getBlockBuildableTargetCells(0).getPosition(1, 1)
        );
        // End Sanity check

        testHarness.getTurnEventsManager().processBeforeBuildEvents(testHarness.getTurn());

        assertTrue(
                testHarness.getBlockBuildableTargetCells(0).getPosition(1, 1)
        );
        assertEquals(
                testHarness.getGame().getBoard().getTargets(
                        testHarness.getBlockBuildableTargetCells(0)
                ).size(),
                1
        );
        assertEquals(
                testHarness.getGame().getBoard().getTargets(
                        testHarness.getDomeBuildableTargetCells(0)
                ).size(),
                0
        );
    }

    @Test
    void doNotAllowBuildDomeOnPreviousCell() {
        testHarness.setCell(0, 0, GodsTestHarness.MockedPlayer.OWNER.player, false, 2);
        testHarness.setCell(1, 1, false, 3);

        testHarness.addBuildAction(
                1, 1,
                Component.BLOCK.getInstance(),
                2,
                0
        );
        testHarness.commitState();

        // Sanity check
        assertTrue(
                testHarness.getDomeBuildableTargetCells(0).getPosition(1, 1)
        );
        // End Sanity check

        testHarness.getTurnEventsManager().processBeforeBuildEvents(testHarness.getTurn());

        assertEquals(
                testHarness.getGame().getBoard().getTargets(
                        testHarness.getBlockBuildableTargetCells(0)
                ).size(),
                0
        );
        assertEquals(
                testHarness.getGame().getBoard().getTargets(
                        testHarness.getDomeBuildableTargetCells(0)
                ).size(),
                0
        );
    }

    @Test
    void doNotChangeAfterSecondBuild() {
        testHarness.setCell(0, 0, GodsTestHarness.MockedPlayer.OWNER.player, false, 2);
        testHarness.setCell(1, 1, false, 2);

        testHarness.addBuildAction(
                1, 1,
                Component.BLOCK.getInstance(),
                0,
                0
        );
        testHarness.addBuildAction(
                1, 1,
                Component.BLOCK.getInstance(),
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
    void allowSecondMoveAfterFirst() {
        testHarness.setCell(0, 0, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);
        testHarness.setCell(1, 1, false, 1);

        testHarness.addBuildAction(
                1, 1,
                Component.BLOCK.getInstance(),
                0,
                0
        );

        testHarness.commitState();

        testHarness.getTurnEventsManager().processAfterBuildEvents(testHarness.getTurn());

        verify(
                testHarness.getTurn()
        ).setNextState(TurnState.BUILD.getTurnState());
    }
}
