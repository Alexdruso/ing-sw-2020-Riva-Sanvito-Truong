package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.reducedmodel.ReducedGame;
import it.polimi.ingsw.client.reducedmodel.ReducedPlayer;
import it.polimi.ingsw.client.reducedmodel.ReducedTurn;
import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AbstractClientStateTestHarness {
    private final Client mockClient = mock(Client.class);
    private final Connection mockConnection = mock(Connection.class);
    private final ReducedGame mockGame = mock(ReducedGame.class);
    private final ReducedTurn mockTurn = mock(ReducedTurn.class);
    private final AbstractClientTurnState mockTurnState = mock(AbstractClientTurnState.class);

    AbstractClientStateTestHarness() {
        when(mockClient.getConnection()).thenReturn(mockConnection);
        when(mockClient.getGame()).thenReturn(mockGame);
        when(mockGame.getTurn()).thenReturn(mockTurn);
        when(mockTurn.getTurnState()).thenReturn(mockTurnState);
    }


    Client getClient() {
        return mockClient;
    }

    Connection getConnection() {
        return mockConnection;
    }

    AbstractClientTurnState getTurnState() {
        return mockTurnState;
    }
}
