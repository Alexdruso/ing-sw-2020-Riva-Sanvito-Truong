package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.utils.networking.transmittables.clientmessages.ClientChooseGodMessage;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class AbstractAskGodFromListClientStateTest {
    private static class ClientStateToTest extends AbstractAskGodFromListClientState {
        private ClientStateToTest() {
            super(AbstractClientStateTestHarness.getClient());
            AbstractClientStateTestHarness.initMocks();
        }

        @Override
        public void render() {}
    }

    private final ClientStateToTest clientState = spy(new ClientStateToTest());

    @Test
    void setup() {
        clientState.setup();
        verify(AbstractClientStateTestHarness.getClient()).requestRender();
    }

    @Test
    void notifyUiInteraction() {
        clientState.notifyUiInteraction();
        verify(AbstractClientStateTestHarness.getConnection()).send(any(ClientChooseGodMessage.class));
    }
}