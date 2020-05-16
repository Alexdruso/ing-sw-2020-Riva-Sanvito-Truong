package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.workers.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collection;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class HypnusTest {
    private GodsTestHarness testHarness;

    @BeforeEach
    void initGodTestHarness() {
        testHarness = new GodsTestHarness(GodsTestHarness.MockedPlayer.OPPONENT1, GodCard.HYPNUS.getGod());
    }

    @Test
    void oneWorkerHigherThanOtherAtBeginningOfTurn() {
        testHarness.setCell(2, 2, GodsTestHarness.MockedPlayer.OWNER.player, false, 2);
        testHarness.setCell(4, 4, GodsTestHarness.MockedPlayer.OWNER.player, false, 0);

        testHarness.commitState();

        testHarness.getTurnEventsManager().processBeforeMovementEvents(testHarness.getTurn());

        verify(testHarness.getTurn()).clearAllowedWorkers();

        Class<Collection<Worker>> collectionClass =
                (Class<Collection<Worker>>) (Class) Collection.class;
        verify(testHarness.getTurn()).addAllowedWorkers(Mockito.any(collectionClass));
    }

    @Test
    void noWorkerHigherThanOtherAtBeginningOfTurn() {
        testHarness.setCell(2, 2, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);
        testHarness.setCell(4, 4, GodsTestHarness.MockedPlayer.OWNER.player, false, 1);

        testHarness.commitState();

        testHarness.getTurnEventsManager().processBeforeMovementEvents(testHarness.getTurn());

        verify(testHarness.getTurn(), times(0)).clearAllowedWorkers();

        Class<Collection<Worker>> collectionClass =
                (Class<Collection<Worker>>) (Class) Collection.class;
        verify(testHarness.getTurn(), times(0)).addAllowedWorkers(Mockito.any(collectionClass));
    }
}