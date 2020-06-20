package it.polimi.ingsw.utils.networking.transmittables.servermessages;

import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.clientstates.ClientTurnState;
import it.polimi.ingsw.client.reducedmodel.ReducedTurn;
import it.polimi.ingsw.utils.networking.transmittables.ReducedTargetCells;
import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;
import it.polimi.ingsw.utils.networking.transmittables.ReducedWorkerID;
import it.polimi.ingsw.utils.networking.transmittables.TransmittablesTestHarness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ServerAskMoveMessageTest {
    private TransmittablesTestHarness testHarness;
    private ReducedUser user;
    private boolean isSkippable;
    private List<ReducedWorkerID> allowedWorkers;
    private EnumMap<ReducedWorkerID, ReducedTargetCells> workerWalkableCells;

    @BeforeEach
    void initGodTestHarness() {
        user = mock(ReducedUser.class);
        isSkippable = false;
        allowedWorkers = new ArrayList<>();
        workerWalkableCells = new EnumMap<>(ReducedWorkerID.class);
        testHarness = new TransmittablesTestHarness(new ServerAskMoveMessage(
                user,
                isSkippable,
                allowedWorkers,
                workerWalkableCells
        ));
        testHarness.registerMockedReducedUser(user);
    }

    @Test
    void testClientHandling() {
        testHarness.dispatchClient();
        verify(testHarness.getMockedClient()).setCurrentActiveUser(
                user
        );
        verify(testHarness.getMockedClient()).moveToState(ClientState.IN_GAME);
        verify(testHarness.getMockedReducedGame()).setTurn(any(ReducedTurn.class));
        verify(testHarness.getMockedUI()).getClientTurnState(
                ClientTurnState.MOVE,
                testHarness.getMockedClient()
        );
        verify(testHarness.getMockedClient()).requestRender();
    }
}
