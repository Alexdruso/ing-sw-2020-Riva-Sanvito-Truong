package it.polimi.ingsw.server.model.gods;

import it.polimi.ingsw.server.model.Turn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class AthenaTest {
    private GodsTestHarness testHarness;

    @BeforeEach
    void initGodTestHarness() {
        testHarness = new GodsTestHarness(GodsTestHarness.MockedPlayer.OPPONENT1, GodCard.ATHENA.getGod());
    }

    @Test
    void doNotMoveToHigherLevelWhenAthenaMovedUp() {
        testHarness.setCell(0, 0, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);
        testHarness.setCell(1, 0, false, 2);
        testHarness.setCell(1, 1, GodsTestHarness.MockedPlayer.OPPONENT1.player, false, 1);

        Turn athenaTurn = testHarness.addPreviousRoundTurn(GodsTestHarness.MockedPlayer.OPPONENT1);
        testHarness.addMoveAction(athenaTurn, 0, 1, 1, 1, 0, 1, 0);

        testHarness.commitState();

        testHarness.getTurnEventsManager().processBeforeMovementEvents(testHarness.getTurn());

        assertFalse(testHarness.getWalkableTargetCells(0).getPosition(1, 0));
        assertTrue(testHarness.getWalkableTargetCells(0).getPosition(0, 1));
    }

}
