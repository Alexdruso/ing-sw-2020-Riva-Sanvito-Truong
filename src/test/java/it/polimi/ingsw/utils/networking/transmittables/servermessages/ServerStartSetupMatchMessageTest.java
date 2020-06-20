package it.polimi.ingsw.utils.networking.transmittables.servermessages;

import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;
import it.polimi.ingsw.utils.networking.transmittables.TransmittablesTestHarness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;

public class ServerStartSetupMatchMessageTest {
    private TransmittablesTestHarness testHarness;
    private ReducedUser[] users;

    @BeforeEach
    void initGodTestHarness() {
        users = new ReducedUser[]{};
        testHarness = new TransmittablesTestHarness(new ServerStartSetupMatchMessage(
                users
        ));
    }

    @Test
    void testClientHandling() {
        testHarness.dispatchClient();
        verify(testHarness.getMockedClient()).createGame(
                users
        );
    }
}
