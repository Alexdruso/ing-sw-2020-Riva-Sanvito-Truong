package it.polimi.ingsw.utils.networking.transmittables.servermessages;

import it.polimi.ingsw.utils.networking.transmittables.ReducedGod;
import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;
import it.polimi.ingsw.utils.networking.transmittables.TransmittablesTestHarness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ServerSetGodMessageTest {
    private TransmittablesTestHarness testHarness;
    private ReducedUser user;
    private ReducedGod god;

    @BeforeEach
    void initGodTestHarness() {
        user = mock(ReducedUser.class);
        god = mock(ReducedGod.class);
        testHarness = new TransmittablesTestHarness(new ServerSetGodMessage(
                user,
                god
        ));
        testHarness.registerMockedReducedUser(user);
    }

    @Test
    void testClientHandling() {
        testHarness.dispatchClient();
        assertTrue(testHarness.getMockedReducedGame().getPlayer(user).isPresent());
        testHarness.getMockedReducedGame().getPlayer(user).ifPresent(mockedPlayer -> verify(mockedPlayer).setGod(god));
    }
}
