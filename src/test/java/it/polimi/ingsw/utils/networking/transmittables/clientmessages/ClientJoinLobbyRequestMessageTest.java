package it.polimi.ingsw.utils.networking.transmittables.clientmessages;

import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.transmittables.StatusMessages;
import it.polimi.ingsw.utils.networking.transmittables.TransmittablesTestHarness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ClientJoinLobbyRequestMessageTest {
    private TransmittablesTestHarness testHarness;

    @BeforeEach
    void initGodTestHarness() {
        testHarness = new TransmittablesTestHarness(new ClientJoinLobbyMessage());
    }

    @Test
    void testServerConnectionSetupHandlerHandlingNicknameOk() {
        when(testHarness.getMockedServerLobbyBuilder().handleLobbyRequest(anyString(), any(Connection.class))).thenReturn(true);

        assertTrue(testHarness.dispatchServerConnectionSetupHandler());
        verify(testHarness.getMockedServerLobbyBuilder()).handleLobbyRequest(
                testHarness.getMockedNickname(),
                testHarness.getMockedConnection()
        );
    }

    @Test
    void testServerConnectionSetupHandlerHandlingNicknameKo() {
        when(testHarness.getMockedServerLobbyBuilder().handleLobbyRequest(anyString(), any(Connection.class))).thenReturn(false);

        assertFalse(testHarness.dispatchServerConnectionSetupHandler());
        verify(testHarness.getMockedServerLobbyBuilder()).handleLobbyRequest(
                testHarness.getMockedNickname(),
                testHarness.getMockedConnection()
        );
        verify(testHarness.getMockedConnection()).send(StatusMessages.CLIENT_ERROR);
    }
}