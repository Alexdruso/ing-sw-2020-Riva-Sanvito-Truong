package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.reducedmodel.ReducedGame;
import it.polimi.ingsw.client.reducedmodel.ReducedPlayer;
import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AbstractClientTurnStateTestHarness {
    private final Client mockClient = mock(Client.class);
    private final Connection mockConnection = mock(Connection.class);
    private final ReducedGame mockGame = mock(ReducedGame.class);
    private final String mockNickname = "test";
    private final ReducedUser mockUser = new ReducedUser(mockNickname);
    private final ReducedPlayer mockPlayer = new ReducedPlayer(mockUser, true, 0);

    AbstractClientTurnStateTestHarness() {
        when(mockClient.getConnection()).thenReturn(mockConnection);
        when(mockClient.getGame()).thenReturn(mockGame);
        when(mockClient.getNickname()).thenReturn(mockNickname);
        when(mockGame.getPlayer(any(String.class))).thenAnswer(call ->
                Optional.of(mockPlayer)
        );
    }

    Client getClient() {
        return mockClient;
    }

    Connection getConnection() {
        return mockConnection;
    }
}
