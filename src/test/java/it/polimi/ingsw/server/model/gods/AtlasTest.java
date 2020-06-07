package it.polimi.ingsw.server.model.gods;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AtlasTest {
    private GodsTestHarness testHarness;

    @BeforeEach
    void initGodTestHarness() {
        testHarness = new GodsTestHarness(GodCard.ATLAS.getGod());
    }

    @Test
    void allowBuildDomeAtAnyLevel() {
        testHarness.setCell(2, 2, GodsTestHarness.MockedPlayer.OWNER.player, false, 0);
        testHarness.setCell(1, 2, false, 3);
        testHarness.setCell(3, 2, true, 1);
        testHarness.setCell(2, 1, false, 2);
        testHarness.setCell(2, 3, true, 3);
        testHarness.setCell(3, 3, false, 0);
        testHarness.setCell(1, 3, true, 0);
        testHarness.setCell(1, 1, false, 1);

        testHarness.commitState();

        // Sanity check
        assertFalse(testHarness.getDomeBuildableTargetCells(0).getPosition(2, 2));
        assertTrue(testHarness.getDomeBuildableTargetCells(0).getPosition(1, 2));
        assertFalse(testHarness.getDomeBuildableTargetCells(0).getPosition(3, 2));
        assertFalse(testHarness.getDomeBuildableTargetCells(0).getPosition(2, 1));
        assertFalse(testHarness.getDomeBuildableTargetCells(0).getPosition(2, 3));
        assertFalse(testHarness.getDomeBuildableTargetCells(0).getPosition(3, 3));
        assertFalse(testHarness.getDomeBuildableTargetCells(0).getPosition(1, 3));
        assertFalse(testHarness.getDomeBuildableTargetCells(0).getPosition(1, 1));
        // End Sanity check

        testHarness.getTurnEventsManager().processBeforeBuildEvents(testHarness.getTurn());

        assertFalse(testHarness.getDomeBuildableTargetCells(0).getPosition(2, 2));
        assertTrue(testHarness.getDomeBuildableTargetCells(0).getPosition(1, 2));
        assertFalse(testHarness.getDomeBuildableTargetCells(0).getPosition(3, 2));
        assertTrue(testHarness.getDomeBuildableTargetCells(0).getPosition(2, 1));
        assertFalse(testHarness.getDomeBuildableTargetCells(0).getPosition(2, 3));
        assertTrue(testHarness.getDomeBuildableTargetCells(0).getPosition(3, 3));
        assertFalse(testHarness.getDomeBuildableTargetCells(0).getPosition(1, 3));
        assertTrue(testHarness.getDomeBuildableTargetCells(0).getPosition(1, 1));
    }

}
