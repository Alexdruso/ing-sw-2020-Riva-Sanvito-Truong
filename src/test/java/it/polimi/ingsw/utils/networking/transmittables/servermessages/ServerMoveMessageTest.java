package it.polimi.ingsw.utils.networking.transmittables.servermessages;

import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;
import it.polimi.ingsw.utils.networking.transmittables.ReducedWorkerID;
import it.polimi.ingsw.utils.networking.transmittables.TransmittablesTestHarness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ServerMoveMessageTest {
    private TransmittablesTestHarness testHarness;
    private ReducedUser user;
    private int sourceCellX;
    private int sourceCellY;
    private int targetCellX;
    private int targetCellY;
    private ReducedWorkerID workerID;

    @BeforeEach
    void initGodTestHarness() {
        user = mock(ReducedUser.class);
        sourceCellX = 0;
        sourceCellY = 1;
        targetCellX = 2;
        targetCellY = 3;
        workerID = ReducedWorkerID.WORKER1;
        testHarness = new TransmittablesTestHarness(new ServerMoveMessage(
                user,
                sourceCellX,
                sourceCellY,
                targetCellX,
                targetCellY,
                workerID
        ));
        testHarness.registerMockedReducedUser(user);
    }

    @Test
    void testClientHandling() {
        testHarness.dispatchClient();
        verify(testHarness.getMockedReducedGame()).setWorkerCell(
                user,
                workerID,
                sourceCellX,
                sourceCellY,
                targetCellX,
                targetCellY
        );
    }
}
