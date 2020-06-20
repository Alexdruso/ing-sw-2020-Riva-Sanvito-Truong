package it.polimi.ingsw.utils.networking.transmittables.servermessages;

import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;
import it.polimi.ingsw.utils.networking.transmittables.TransmittablesTestHarness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ServerLoseGameMessageTest {
    private TransmittablesTestHarness testHarness;
    private ReducedUser user;

    @BeforeEach
    void initGodTestHarness() {
        user = mock(ReducedUser.class);
        testHarness = new TransmittablesTestHarness(new ServerLoseGameMessage(
                user
        ));
        testHarness.registerMockedReducedUser(user);
    }

    @Test
    void testClientHandling() {
        testHarness.dispatchClient();
        verify(testHarness.getMockedClient()).setCurrentActiveUser(
                user
        );
        assertTrue(testHarness.getMockedReducedGame().getPlayer(user).isPresent());
        testHarness.getMockedReducedGame().getPlayer(user).ifPresent(mockedPlayer -> verify(mockedPlayer).setInGame(false));
        verify(testHarness.getMockedClient()).moveToState(ClientState.LOSE_GAME);
    }
}
