package it.polimi.ingsw.utils.networking.transmittables.clientmessages;

import it.polimi.ingsw.utils.networking.transmittables.ReducedWorkerID;
import it.polimi.ingsw.utils.networking.transmittables.TransmittablesTestHarness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;

class ClientSetWorkerStartPositionMessageTest {
    private TransmittablesTestHarness testHarness;
    private int targetCellX;
    private int targetCellY;
    private ReducedWorkerID workerID;

    @BeforeEach
    void initGodTestHarness() {
        targetCellX = 0;
        targetCellY = 1;
        workerID = ReducedWorkerID.WORKER1;
        testHarness = new TransmittablesTestHarness(new ClientSetWorkerStartPositionMessage(
                targetCellX,
                targetCellY,
                workerID
        ));
    }

    @Test
    void testControllerHandling() {
        testHarness.dispatchController();
        verify(testHarness.getMockedController()).dispatchSetWorkerStartPositionAction(
                (ClientSetWorkerStartPositionMessage) testHarness.getTransmittable(),
                testHarness.getMockedView(),
                testHarness.getMockedUser()
        );
    }
}