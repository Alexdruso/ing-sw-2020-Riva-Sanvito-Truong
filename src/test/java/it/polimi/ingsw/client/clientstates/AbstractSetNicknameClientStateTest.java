package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.utils.networking.transmittables.clientmessages.ClientChooseGodsMessage;
import it.polimi.ingsw.utils.networking.transmittables.clientmessages.ClientSetNicknameMessage;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class AbstractSetNicknameClientStateTest {
    AbstractClientStateTestHarness testHarness = new AbstractClientStateTestHarness();

    private class ClientStateToTest extends AbstractSetNicknameClientState {
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
        verify(testHarness.getClient()).requestRender();
    }

    @Test
    void notifyUiInteraction() {
        clientState.notifyUiInteraction();
        verify(testHarness.getConnection()).send(any(ClientSetNicknameMessage.class));
    }
}