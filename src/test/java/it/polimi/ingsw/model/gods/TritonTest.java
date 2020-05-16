package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.turnstates.TurnState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TritonTest {

    private GodsTestHarness testHarness;

    @BeforeEach
    void initGodTestHarness() {
        testHarness = new GodsTestHarness(GodCard.TRITON.getGod());
    }

    @Test
    void neverMovedInPerimeter() {
        testHarness.setCell(1, 1, GodsTestHarness.MockedPlayer.OWNER.player, false, 2);

        testHarness.addMoveAction(
                0, 0,
                1, 1,
                0,
                0,
                0
        );

        testHarness.commitState();

        testHarness.getTurnEventsManager().processAfterMovementEvents(testHarness.getTurn());
        verify(testHarness.getTurn(), times(0)).setNextState(TurnState.MOVE.getTurnState());


        testHarness.getTurnEventsManager().processBeforeMovementEvents(testHarness.getTurn());
        verify(testHarness.getTurn(), times(0)).setSkippable(true);
    }

    @Test
    void movedInPerimeter() {
        testHarness.setCell(0, 0, GodsTestHarness.MockedPlayer.OWNER.player, false, 2);

        testHarness.addMoveAction(
                1, 1,
                0, 0,
                0,
                0,
                0
        );

        testHarness.commitState();

        testHarness.getTurnEventsManager().processAfterMovementEvents(testHarness.getTurn());
        verify(testHarness.getTurn(), times(1)).setNextState(TurnState.MOVE.getTurnState());

        testHarness.getTurnEventsManager().processBeforeMovementEvents(testHarness.getTurn());
        verify(testHarness.getTurn(), times(1)).setSkippable(true);
    }

    @Test
    void multipleMovesInPerimeter() {
        testHarness.setCell(4, 4, GodsTestHarness.MockedPlayer.OWNER.player, false, 2);

        testHarness.addMoveAction(
                4, 2,
                4, 3,
                0,
                0,
                0
        );

        testHarness.addMoveAction(
                4, 3,
                4, 2,
                0,
                0,
                0
        );

        testHarness.commitState();

        testHarness.getTurnEventsManager().processAfterMovementEvents(testHarness.getTurn());
        verify(testHarness.getTurn(), times(1)).setNextState(TurnState.MOVE.getTurnState());

        testHarness.getTurnEventsManager().processBeforeMovementEvents(testHarness.getTurn());
        verify(testHarness.getTurn(), times(1)).setSkippable(true);
    }

    @Test
    void lastMoveOutOfPerimeter() {
        testHarness.setCell(3, 3, GodsTestHarness.MockedPlayer.OWNER.player, false, 2);

        testHarness.addMoveAction(
                4, 2,
                4, 3,
                0,
                0,
                0
        );

        testHarness.addMoveAction(
                4, 3,
                3, 3,
                0,
                0,
                0
        );

        testHarness.commitState();

        testHarness.getTurnEventsManager().processAfterMovementEvents(testHarness.getTurn());
        verify(testHarness.getTurn(), times(0)).setNextState(TurnState.MOVE.getTurnState());

        testHarness.getTurnEventsManager().processBeforeMovementEvents(testHarness.getTurn());
        verify(testHarness.getTurn(), times(0)).setSkippable(true);
    }
}