package it.polimi.ingsw.utils.networking.transmittables.clientmessages;

import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.transmittables.StatusMessages;
import it.polimi.ingsw.utils.networking.transmittables.TransmittablesTestHarness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ClientSetNicknameMessageTest {
    private TransmittablesTestHarness testHarness;
    private String nickname;

    @BeforeEach
    void initGodTestHarness() {
        nickname = "";
        testHarness = new TransmittablesTestHarness(new ClientSetNicknameMessage(
                nickname
        ));
    }

    @Test
    void testServerConnectionSetupHandlerHandlingNicknameOk() {
        when(testHarness.getMockedServerLobbyBuilder().registerNickname(anyString(), any(Connection.class))).thenReturn(true);

        assertTrue(testHarness.dispatchServerConnectionSetupHandler());
        verify(testHarness.getMockedServerLobbyBuilder()).registerNickname(
                nickname,
                testHarness.getMockedConnection()
        );
        verify(testHarness.getMockedConnection()).send(StatusMessages.OK);
    }

    @Test
    void testServerConnectionSetupHandlerHandlingNicknameKo() {
        when(testHarness.getMockedServerLobbyBuilder().registerNickname(anyString(), any(Connection.class))).thenReturn(false);

        assertFalse(testHarness.dispatchServerConnectionSetupHandler());
        verify(testHarness.getMockedServerLobbyBuilder()).registerNickname(
                testHarness.getMockedNickname(),
                testHarness.getMockedConnection()
        );
        verify(testHarness.getMockedConnection()).send(StatusMessages.CLIENT_ERROR);
    }
}