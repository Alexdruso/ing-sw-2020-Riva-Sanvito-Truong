package it.polimi.ingsw.utils.networking.transmittables.clientmessages;

import it.polimi.ingsw.utils.networking.transmittables.ReducedComponent;
import it.polimi.ingsw.utils.networking.transmittables.ReducedWorkerID;
import it.polimi.ingsw.utils.networking.transmittables.TransmittablesTestHarness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;

class ClientBuildMessageTest {
    private TransmittablesTestHarness testHarness;
    private int targetCellX;
    private int targetCellY;
    private ReducedComponent component;
    private int builtLevel;
    private ReducedWorkerID workerID;

    @BeforeEach
    void initGodTestHarness() {
        targetCellX = 0;
        targetCellY = 1;
        component = ReducedComponent.BLOCK;
        builtLevel = 2;
        workerID = ReducedWorkerID.WORKER1;
        testHarness = new TransmittablesTestHarness(new ClientBuildMessage(
                targetCellX,
                targetCellY,
                component,
                builtLevel,
                workerID
        ));
    }

    @Test
    void testControllerHandling() {
        testHarness.dispatchController();
        verify(testHarness.getMockedController()).dispatchBuildAction(
                (ClientBuildMessage) testHarness.getTransmittable(),
                testHarness.getMockedView(),
                testHarness.getMockedUser()
        );
    }
}