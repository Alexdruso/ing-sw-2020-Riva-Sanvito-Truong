package it.polimi.ingsw.utils.networking.transmittables.servermessages;

import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;
import it.polimi.ingsw.utils.networking.transmittables.TransmittablesTestHarness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ServerAskStartPlayerMessageTest {
    private TransmittablesTestHarness testHarness;
    private ReducedUser user;

    @BeforeEach
    void initGodTestHarness() {
        user = mock(ReducedUser.class);
        testHarness = new TransmittablesTestHarness(new ServerAskStartPlayerMessage(
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
        verify(testHarness.getMockedClient()).moveToState(ClientState.ASK_START_PLAYER);
    }
}
