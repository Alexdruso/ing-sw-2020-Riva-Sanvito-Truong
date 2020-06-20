package it.polimi.ingsw.utils.networking.transmittables.clientmessages;

import it.polimi.ingsw.utils.networking.transmittables.ReducedWorkerID;
import it.polimi.ingsw.utils.networking.transmittables.TransmittablesTestHarness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;

class ClientMoveMessageTest {
    private TransmittablesTestHarness testHarness;
    private int sourceCellX;
    private int sourceCellY;
    private int targetCellX;
    private int targetCellY;
    private ReducedWorkerID workerID;

    @BeforeEach
    void initGodTestHarness() {
        sourceCellX = 0;
        sourceCellY = 1;
        targetCellX = 2;
        targetCellY = 3;
        workerID = ReducedWorkerID.WORKER1;
        testHarness = new TransmittablesTestHarness(new ClientMoveMessage(
                sourceCellX,
                sourceCellY,
                targetCellX,
                targetCellY,
                workerID
        ));
    }

    @Test
    void testControllerHandling() {
        testHarness.dispatchController();
        verify(testHarness.getMockedController()).dispatchMoveAction(
                (ClientMoveMessage) testHarness.getTransmittable(),
                testHarness.getMockedView(),
                testHarness.getMockedUser()
        );
    }
}