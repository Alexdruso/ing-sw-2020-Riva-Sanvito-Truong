package it.polimi.ingsw.model.gods;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


class HeraTest {
    private GodsTestHarness testHarness;

    @BeforeEach
    void initGodTestHarness() {
        testHarness = new GodsTestHarness(GodsTestHarness.MockedPlayer.OPPONENT1, GodCard.HERA.getGod());
    }

    @Test
    void doNotAllowWinInPerimeter() {
        testHarness.setCell(0, 0, GodsTestHarness.MockedPlayer.OWNER.player, false, 3);
        testHarness.setCell(1, 1, false, 2);

        testHarness.addMoveAction(1, 1, 0, 0, 2, 3, 0);
        testHarness.setMockedWinCondition(MockedTurnWinCondition.WIN);

        testHarness.commitState();

        testHarness.getTurnEventsManager().processWinConditionEvents(testHarness.getTurn());

        verify(testHarness.getTurn()).setNeutralTurn();
    }


    @Test
    void doNotPreventWinOutsidePerimeter() {
        testHarness.setCell(1, 1, GodsTestHarness.MockedPlayer.OWNER.player, false, 3);
        testHarness.setCell(0, 0, false, 2);

        testHarness.addMoveAction(0, 0, 1, 1, 2, 3, 0);
        testHarness.setMockedWinCondition(MockedTurnWinCondition.WIN);

        testHarness.commitState();

        testHarness.getTurnEventsManager().processWinConditionEvents(testHarness.getTurn());

        verify(testHarness.getTurn(), never()).setNeutralTurn();
    }
}
