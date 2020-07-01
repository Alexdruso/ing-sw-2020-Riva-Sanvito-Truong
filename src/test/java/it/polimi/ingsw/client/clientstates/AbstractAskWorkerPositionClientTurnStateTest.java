package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.utils.networking.transmittables.clientmessages.ClientSetStartPlayerMessage;
import it.polimi.ingsw.utils.networking.transmittables.clientmessages.ClientSetWorkerStartPositionMessage;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class AbstractAskWorkerPositionClientTurnStateTest {
    AbstractClientTurnStateTestHarness testHarness = new AbstractClientTurnStateTestHarness();

    private class ClientTurnStateToTest extends AbstractAskWorkerPositionClientTurnState {
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
        verify(testHarness.getConnection()).send(any(ClientSetWorkerStartPositionMessage.class));
    }
}