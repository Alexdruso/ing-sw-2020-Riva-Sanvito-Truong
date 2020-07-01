package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.transmittables.clientmessages.ClientSetStartPlayerMessage;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AbstractConnectToServerClientStateTest {
    AbstractClientStateTestHarness testHarness = new AbstractClientStateTestHarness();

    private class ClientStateToTest extends AbstractConnectToServerClientState {
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
        assertThrows(IllegalArgumentException.class, clientState::notifyUiInteraction);
    }
}