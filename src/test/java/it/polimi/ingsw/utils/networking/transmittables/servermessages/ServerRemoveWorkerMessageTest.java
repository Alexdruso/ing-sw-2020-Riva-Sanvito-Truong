package it.polimi.ingsw.utils.networking.transmittables.servermessages;

import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;
import it.polimi.ingsw.utils.networking.transmittables.ReducedWorkerID;
import it.polimi.ingsw.utils.networking.transmittables.TransmittablesTestHarness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ServerRemoveWorkerMessageTest {
    private TransmittablesTestHarness testHarness;
    private ReducedUser user;
    private int cellX;
    private int cellY;
    private ReducedWorkerID workerID;

    @BeforeEach
    void initGodTestHarness() {
        user = mock(ReducedUser.class);
        cellX = 0;
        cellY = 1;
        workerID = ReducedWorkerID.WORKER1;
        testHarness = new TransmittablesTestHarness(new ServerRemoveWorkerMessage(
                user,
                cellX, cellY, workerID
        ));
        testHarness.registerMockedReducedUser(user);
    }

    @Test
    void testClientHandling() {
        testHarness.dispatchClient();
        verify(testHarness.getMockedReducedGame()).removeWorker(
                user,
                workerID,
                cellX,
                cellY
        );
    }
}
