package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.networking.Connection;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AbstractClientStateTestHarness {
    private static final Client mockClient = mock(Client.class);
    private static final Connection mockConnection = mock(Connection.class);

    static void initMocks() {
        when(mockClient.getConnection()).thenReturn(mockConnection);
    }

    static Client getClient() {
        return mockClient;
    }

    static Connection getConnection() {
        return mockConnection;
    }
}
