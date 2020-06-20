package it.polimi.ingsw.utils.networking.transmittables.clientmessages;

import it.polimi.ingsw.utils.networking.transmittables.ReducedGod;
import it.polimi.ingsw.utils.networking.transmittables.TransmittablesTestHarness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

class ClientChooseGodsMessageTest {
    private TransmittablesTestHarness testHarness;
    private List<ReducedGod> gods;

    @BeforeEach
    void initGodTestHarness() {
        gods = new ArrayList<>();
        testHarness = new TransmittablesTestHarness(new ClientChooseGodsMessage(
                gods
        ));
    }

    @Test
    void testControllerHandling() {
        testHarness.dispatchController();
        verify(testHarness.getMockedController()).dispatchChooseGodsAction(
                (ClientChooseGodsMessage) testHarness.getTransmittable(),
                testHarness.getMockedView(),
                testHarness.getMockedUser()
        );
    }
}