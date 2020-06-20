package it.polimi.ingsw.utils.networking.transmittables;

import it.polimi.ingsw.client.clientstates.ClientState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;

class DisconnectionMessageTest {
    private TransmittablesTestHarness testHarness;

    @BeforeEach
    void initGodTestHarness() {
        testHarness = new TransmittablesTestHarness(new DisconnectionMessage());
    }

    @Test
    void testClientHandling() {
        testHarness.dispatchClient();
        verify(testHarness.getMockedClient()).moveToState(ClientState.DISCONNECT);
    }

    @Test
    void testControllerHandling() {
        testHarness.dispatchController();
        verify(testHarness.getMockedController()).dispatchDrawAction(
                testHarness.getMockedView(),
                testHarness.getMockedUser()
        );
    }

    @Test
    void testServerConnectionSetupHandlerHandling() {
        testHarness.dispatchServerConnectionSetupHandler();
        verify(testHarness.getMockedServerLobbyBuilder()).handleDisconnection(
                testHarness.getMockedConnection()
        );
    }

}