package it.polimi.ingsw.utils.networking.transmittables.clientmessages;

import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;
import it.polimi.ingsw.utils.networking.transmittables.TransmittablesTestHarness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ClientSetStartPlayerMessageTest {
    private TransmittablesTestHarness testHarness;
    private ReducedUser user;

    @BeforeEach
    void initGodTestHarness() {
        user = mock(ReducedUser.class);
        testHarness = new TransmittablesTestHarness(new ClientSetStartPlayerMessage(
                user
        ));
    }

    @Test
    void testControllerHandling() {
        testHarness.dispatchController();
        verify(testHarness.getMockedController()).dispatchSetStartPlayerAction(
                (ClientSetStartPlayerMessage) testHarness.getTransmittable(),
                testHarness.getMockedView(),
                testHarness.getMockedUser()
        );
    }
}