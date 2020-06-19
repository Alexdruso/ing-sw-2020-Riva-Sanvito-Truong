package it.polimi.ingsw.utils.networking.transmittables.servermessages;

import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.utils.networking.transmittables.ReducedGod;
import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;
import it.polimi.ingsw.utils.networking.transmittables.TransmittablesTestHarness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ServerAskGodFromListMessageTest {
    private TransmittablesTestHarness testHarness;
    private ReducedUser user;
    private List<ReducedGod> godsList;

    @BeforeEach
    void initGodTestHarness() {
        user = mock(ReducedUser.class);
        godsList = new ArrayList<>();
        testHarness = new TransmittablesTestHarness(new ServerAskGodFromListMessage(
                user,
                godsList
        ));
        testHarness.registerMockedReducedUser(user);
    }

    @Test
    void testClientHandling() {
        testHarness.dispatchClient();
        verify(testHarness.getMockedClient()).setCurrentActiveUser(
                user
        );
        verify(testHarness.getMockedClient()).setGods(
                godsList
        );
        verify(testHarness.getMockedClient()).moveToState(ClientState.ASK_GOD_FROM_LIST);
    }
}
