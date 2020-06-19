package it.polimi.ingsw.utils.networking.transmittables.servermessages;

import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.clientstates.ClientTurnState;
import it.polimi.ingsw.client.reducedmodel.ReducedTurn;
import it.polimi.ingsw.utils.networking.transmittables.ReducedTargetCells;
import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;
import it.polimi.ingsw.utils.networking.transmittables.ReducedWorkerID;
import it.polimi.ingsw.utils.networking.transmittables.TransmittablesTestHarness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ServerAskWorkerPositionMessageTest {
    private TransmittablesTestHarness testHarness;
    private ReducedUser user;
    private ReducedWorkerID workerID;
    private ReducedTargetCells targetCells;

    @BeforeEach
    void initGodTestHarness() {
        user = mock(ReducedUser.class);
        workerID = ReducedWorkerID.WORKER1;
        targetCells = mock(ReducedTargetCells.class);
        testHarness = new TransmittablesTestHarness(new ServerAskWorkerPositionMessage(
                user,
                workerID,
                targetCells
        ));
        testHarness.registerMockedReducedUser(user);
    }

    @Test
    void testClientHandling() {
        testHarness.dispatchClient();
        verify(testHarness.getMockedClient()).setCurrentActiveUser(
                user
        );
        verify(testHarness.getMockedClient()).moveToState(ClientState.IN_GAME);
        verify(testHarness.getMockedReducedGame()).setTurn(any(ReducedTurn.class));
        verify(testHarness.getMockedUI()).getClientTurnState(
                ClientTurnState.ASK_WORKER_POSITION,
                testHarness.getMockedClient()
        );
        verify(testHarness.getMockedClient()).requestRender();
    }
}
