package it.polimi.ingsw.server.model.gods;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ZeusTest {

    private GodsTestHarness testHarness;

    @BeforeEach
    void initGodTestHarness() {
        testHarness = new GodsTestHarness(GodCard.ZEUS.getGod());
    }

    @Test
    void allowBuildInWorkerPosition() {
        testHarness.setCell(0, 0, GodsTestHarness.MockedPlayer.OWNER.player, false, 2);
        testHarness.setCell(4, 4, GodsTestHarness.MockedPlayer.OWNER.player, false, 3);

        testHarness.commitState();

        testHarness.getTurnEventsManager().processBeforeBuildEvents(testHarness.getTurn());

        assertTrue(testHarness.getBlockBuildableTargetCells(0).getPosition(0, 0));
        //can't build if at max level (3)
        assertFalse(testHarness.getBlockBuildableTargetCells(1).getPosition(4, 4));
    }
}