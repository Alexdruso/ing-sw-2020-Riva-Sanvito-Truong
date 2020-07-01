package it.polimi.ingsw.client.clientstates;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class AbstractInGameClientStateTest {
    AbstractClientStateTestHarness testHarness = new AbstractClientStateTestHarness();

    private class ClientStateToTest extends AbstractInGameClientState {
        private ClientStateToTest() {
            super(testHarness.getClient());
        }

        @Override
        public void render() {}
    }

    private final ClientStateToTest clientState = spy(new ClientStateToTest());

    @Test
    void notifyUiInteraction() {
        clientState.notifyUiInteraction();
        verify(testHarness.getTurnState()).notifyUiInteraction();
    }
}