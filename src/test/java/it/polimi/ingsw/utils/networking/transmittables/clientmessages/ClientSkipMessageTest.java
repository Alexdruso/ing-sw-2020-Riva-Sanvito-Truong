package it.polimi.ingsw.utils.networking.transmittables.clientmessages;

import it.polimi.ingsw.utils.networking.transmittables.TransmittablesTestHarness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;

class ClientSkipMessageTest {
    private TransmittablesTestHarness testHarness;

    @BeforeEach
    void initGodTestHarness() {
        testHarness = new TransmittablesTestHarness(new ClientSkipMessage());
    }

    @Test
    void testControllerHandling() {
        testHarness.dispatchController();
        verify(testHarness.getMockedController()).dispatchSkipAction(
                testHarness.getMockedView(),
                testHarness.getMockedUser()
        );
    }
}