package it.polimi.ingsw.utils.networking.transmittables.servermessages;

import it.polimi.ingsw.utils.networking.transmittables.ReducedComponent;
import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;
import it.polimi.ingsw.utils.networking.transmittables.ReducedWorkerID;
import it.polimi.ingsw.utils.networking.transmittables.TransmittablesTestHarness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ServerBuildMessageTest {
    private TransmittablesTestHarness testHarness;
    private ReducedUser user;
    private int targetCellX;
    private int targetCellY;
    private ReducedComponent component;
    private int builtLevel;
    private ReducedWorkerID workerID;

    @BeforeEach
    void initGodTestHarness() {
        user = mock(ReducedUser.class);
        targetCellX = 0;
        targetCellY = 1;
        component = ReducedComponent.BLOCK;
        builtLevel = 2;
        workerID = ReducedWorkerID.WORKER1;
        testHarness = new TransmittablesTestHarness(new ServerBuildMessage(
                user,
                targetCellX,
                targetCellY,
                component,
                builtLevel,
                workerID
        ));
        testHarness.registerMockedReducedUser(user);
    }

    @Test
    void testClientHandling() {
        testHarness.dispatchClient();
        verify(testHarness.getMockedReducedGame()).buildComponentInCell(
                targetCellX,
                targetCellY,
                component,
                builtLevel
        );
    }
}
