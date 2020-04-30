package it.polimi.ingsw.model.gods;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


class PanTest {
    private GodsTestHarness testHarness;

    @BeforeEach
    void initGodTestHarness() {
        testHarness = new GodsTestHarness(GodCard.PAN.getGod());
    }

    @Test
    void neutralConditionDown0() {
        testHarness.setCell(0, 1, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);

        testHarness.addMoveAction(
                0, 0,
                0, 1,
                2,
                2,
                0
        );

        testHarness.commitState();

        testHarness.getTurnEventsManager().processWinConditionEvents(testHarness.getTurn());

        verify(testHarness.getTurn(), never()).setWinningTurn();
    }

    @Test
    void neutralConditionDown1() {
        testHarness.setCell(0, 1, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);

        testHarness.addMoveAction(
                0, 0,
                0, 1,
                2,
                1,
                0
        );

        testHarness.commitState();

        testHarness.getTurnEventsManager().processWinConditionEvents(testHarness.getTurn());

        verify(testHarness.getTurn(), never()).setWinningTurn();
    }

    @Test
    void winConditionDown2() {
        testHarness.setCell(0, 1, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);

        testHarness.addMoveAction(
                0, 0,
                0, 1,
                3,
                1,
                0
        );

        testHarness.commitState();

        testHarness.getTurnEventsManager().processWinConditionEvents(testHarness.getTurn());

        verify(testHarness.getTurn()).setWinningTurn();
    }

    @Test
    void winConditionDown3() {
        testHarness.setCell(0, 1, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);

        testHarness.addMoveAction(
                0, 0,
                0, 1,
                3,
                0,
                0
        );

        testHarness.commitState();

        testHarness.getTurnEventsManager().processWinConditionEvents(testHarness.getTurn());

        verify(testHarness.getTurn()).setWinningTurn();
    }

    @Test
    void neutralConditionUp2() {
        testHarness.setCell(0, 1, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);

        testHarness.addMoveAction(
                0, 0,
                0, 1,
                0,
                2,
                0
        );

        testHarness.commitState();

        testHarness.getTurnEventsManager().processWinConditionEvents(testHarness.getTurn());

        verify(testHarness.getTurn(), never()).setWinningTurn();
    }
}
