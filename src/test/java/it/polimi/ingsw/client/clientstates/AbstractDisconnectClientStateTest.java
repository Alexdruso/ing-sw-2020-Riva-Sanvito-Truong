package it.polimi.ingsw.client.clientstates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class AbstractDisconnectClientStateTest {
    AbstractClientStateTestHarness testHarness = new AbstractClientStateTestHarness();

    private class ClientStateToTest extends AbstractDisconnectClientState {
        private ClientStateToTest() {
            super(testHarness.getClient());
        }

        @Override
        public void render() {}
    }

    private final ClientStateToTest clientState = spy(new ClientStateToTest());

    @Test
    void setup() {
        clientState.setup();
        verify(testHarness.getClient()).closeConnection();
        verify(testHarness.getClient()).requestRender();
    }

    @Test
    void notifyUiInteraction() {
        clientState.notifyUiInteraction();
        verify(testHarness.getClient()).moveToState(ClientState.CONNECT_TO_SERVER);
    }
}