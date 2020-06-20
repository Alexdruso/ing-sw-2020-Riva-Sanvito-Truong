package it.polimi.ingsw.utils.networking.transmittables.clientmessages;

import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.transmittables.StatusMessages;
import it.polimi.ingsw.utils.networking.transmittables.TransmittablesTestHarness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ClientSetPlayersCountMessageTest {
    private TransmittablesTestHarness testHarness;
    private int playersCount;

    @BeforeEach
    void initGodTestHarness() {
        playersCount = 0;
        testHarness = new TransmittablesTestHarness(new ClientSetPlayersCountMessage(
                playersCount
        ));
    }

    @Test
    void testServerConnectionSetupHandlerHandlingOk() {
        when(testHarness.getMockedServerLobbyBuilder().setLobbyMaxPlayerCount(anyInt(), any(Connection.class))).thenReturn(true);

        assertTrue(testHarness.dispatchServerConnectionSetupHandler());
        verify(testHarness.getMockedServerLobbyBuilder()).setLobbyMaxPlayerCount(
                playersCount,
                testHarness.getMockedConnection()
        );
        verify(testHarness.getMockedConnection()).send(StatusMessages.OK);
    }

    @Test
    void testServerConnectionSetupHandlerHandlingKo() {
        when(testHarness.getMockedServerLobbyBuilder().setLobbyMaxPlayerCount(anyInt(), any(Connection.class))).thenReturn(false);

        assertFalse(testHarness.dispatchServerConnectionSetupHandler());
        verify(testHarness.getMockedServerLobbyBuilder()).setLobbyMaxPlayerCount(
                playersCount,
                testHarness.getMockedConnection()
        );
        verify(testHarness.getMockedConnection()).send(StatusMessages.CLIENT_ERROR);
    }
}