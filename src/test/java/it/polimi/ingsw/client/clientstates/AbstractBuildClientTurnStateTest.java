package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.utils.networking.transmittables.ReducedWorkerID;
import it.polimi.ingsw.utils.networking.transmittables.clientmessages.ClientBuildMessage;
import it.polimi.ingsw.utils.networking.transmittables.clientmessages.ClientSetWorkerStartPositionMessage;
import it.polimi.ingsw.utils.networking.transmittables.clientmessages.ClientSkipMessage;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class AbstractBuildClientTurnStateTest {
    AbstractClientTurnStateTestHarness testHarness = new AbstractClientTurnStateTestHarness();

    private class ClientTurnStateToTest extends AbstractBuildClientTurnState {
        private ClientTurnStateToTest() {
            super(testHarness.getClient());
        }

        @Override
        public void render() {}
    }

    private final ClientTurnStateToTest clientTurnState = spy(new ClientTurnStateToTest());

    @Test
    void notifyUiInteraction() {
        clientTurnState.notifyUiInteraction();
        verify(testHarness.getConnection()).send(any(ClientSkipMessage.class));

        clientTurnState.workerID = ReducedWorkerID.WORKER1;
        clientTurnState.notifyUiInteraction();
        verify(testHarness.getConnection()).send(any(ClientBuildMessage.class));
    }
}