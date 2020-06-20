package it.polimi.ingsw.utils.networking.transmittables.clientmessages;

import it.polimi.ingsw.utils.networking.transmittables.ReducedGod;
import it.polimi.ingsw.utils.networking.transmittables.TransmittablesTestHarness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ClientChooseGodMessageTest {
    private TransmittablesTestHarness testHarness;
    private ReducedGod god;

    @BeforeEach
    void initGodTestHarness() {
        god = mock(ReducedGod.class);
        testHarness = new TransmittablesTestHarness(new ClientChooseGodMessage(
                god
        ));
    }

    @Test
    void testControllerHandling() {
        testHarness.dispatchController();
        verify(testHarness.getMockedController()).dispatchChooseGodAction(
                (ClientChooseGodMessage) testHarness.getTransmittable(),
                testHarness.getMockedView(),
                testHarness.getMockedUser()
        );
    }
}