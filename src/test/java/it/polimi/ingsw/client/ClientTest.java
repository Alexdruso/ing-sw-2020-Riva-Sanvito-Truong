package it.polimi.ingsw.client;

import it.polimi.ingsw.client.clientstates.AbstractClientState;
import it.polimi.ingsw.client.clientstates.AbstractClientTurnState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.clientstates.ClientTurnState;
import it.polimi.ingsw.client.ui.UI;
import it.polimi.ingsw.utils.networking.ClientHandleable;
import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.ServerHandleable;
import it.polimi.ingsw.utils.networking.transmittables.ReducedGod;
import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;
import it.polimi.ingsw.utils.networking.transmittables.StatusMessages;
import it.polimi.ingsw.utils.networking.transmittables.Transmittable;
import it.polimi.ingsw.utils.networking.transmittables.clientmessages.ClientMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClientTest {
    private Client client;
    private AbstractClientState mockClientState;
    private AbstractClientTurnState mockClientTurnState;
    private UI mockUI;

    @BeforeEach
    void createClient() {
        mockClientState = mock(AbstractClientState.class);
        mockClientTurnState = mock(AbstractClientTurnState.class);
        mockUI = mock(UI.class);
        client = spy(new Client(mockUI));
        when(mockUI.getClientState(any(ClientState.class), any(Client.class))).thenReturn(mockClientState);
        when(mockUI.getClientTurnState(any(ClientTurnState.class), any(Client.class))).thenReturn(mockClientTurnState);
    }

    @Test
    void testGetUI() {
        assertEquals(mockUI, client.getUI());
    }

    @Test
    void testRun() {
        client.requestExit();
        client.moveToState(ClientState.WELCOME_SCREEN);
        client.requestRender();
        client.run();

        verify(mockClientState).render();
        verify(client).closeConnection();
    }

    @Test
    void testConnection() {
        Connection mockConnection = mock(Connection.class);
        when(mockConnection.isActive()).thenReturn(true);
        client.setConnection(mockConnection);

        assertEquals(mockConnection, client.getConnection());
        assertThrows(IllegalStateException.class, () -> client.setConnection(mockConnection));

        client.closeConnection();
        verify(mockConnection).close();
    }

    @Test
    void testNickname() {
        String nickname = "MyNick";
        client.setNickname(nickname);

        assertEquals(nickname, client.getNickname());
        assertThrows(IllegalStateException.class, () -> client.setNickname(nickname));
    }

    @Test
    void testClientStates() {
        client.moveToState(ClientState.WELCOME_SCREEN);
        assertEquals(mockClientState, client.getCurrentState());
    }

    @Test
    void testUpdateClientError() {
        client.moveToState(ClientState.WELCOME_SCREEN);
        client.update(StatusMessages.CLIENT_ERROR);
        verify(mockClientState).handleClientError();
    }

    @Test
    void testUpdateContinue() {
        client.moveToState(ClientState.WELCOME_SCREEN);
        client.update(StatusMessages.CONTINUE);
        verify(mockClientState).handleContinue();
    }

    @Test
    void testUpdateOk() {
        client.moveToState(ClientState.WELCOME_SCREEN);
        client.update(StatusMessages.OK);
        verify(mockClientState).handleOk();
    }

    @Test
    void testUpdateTeapot() {
        client.moveToState(ClientState.WELCOME_SCREEN);
        client.update(StatusMessages.TEAPOT);
        verify(mockClientState).handleTeapot();
    }

    @Test
    void testUpdateServerError() {
        client.moveToState(ClientState.WELCOME_SCREEN);
        client.update(StatusMessages.SERVER_ERROR);
        verify(mockClientState).handleServerError();
    }

    @Test
    void testUpdateClientHandleable() {
        client.moveToState(ClientState.WELCOME_SCREEN);
        MockedReceivedMessage mockClientHandleable = mock(MockedReceivedMessage.class);
        client.update(mockClientHandleable);
        verify(mockClientHandleable).handleTransmittable(client);
    }

    @Test
    void testUpdateIllegal() {
        client.moveToState(ClientState.WELCOME_SCREEN);
        assertThrows(IllegalStateException.class, () -> client.update(mock(ClientMessage.class)));
    }

    @Test
    void testGame() {
        ReducedUser[] mockReducedUsers = new ReducedUser[]{mock(ReducedUser.class)};
        String mockNickname = "MyNick";
        when(mockReducedUsers[0].getNickname()).thenReturn(mockNickname);
        client.createGame(mockReducedUsers);

        assertTrue(client.getGame().getPlayer(mockReducedUsers[0]).isPresent());
    }

    @Test
    void testGodsAvailableForChoice() {
        List<ReducedGod> mockReducedGods = new ArrayList<>();
        mockReducedGods.add(mock(ReducedGod.class));
        client.setGodsAvailableForChoice(mockReducedGods);

        assertTrue(client.getGodsAvailableForChoice().containsAll(mockReducedGods));
        assertEquals(mockReducedGods.size(), client.getGodsAvailableForChoice().size());
    }

    @Test
    void testActiveUser() {
        String nick1 = "nick1";
        String nick2 = "nick2";
        ReducedUser user1 = mock(ReducedUser.class);
        when(user1.getNickname()).thenReturn(nick1);
        ReducedUser user2 = mock(ReducedUser.class);
        when(user2.getNickname()).thenReturn(nick2);

        assertTrue(client.isCurrentlyActive());

        client.setNickname(nick1);

        client.setCurrentActiveUser(user1);
        assertEquals(user1, client.getCurrentActiveUser());
        assertTrue(client.isCurrentlyActive());

        client.setCurrentActiveUser(user2);
        assertFalse(client.isCurrentlyActive());
    }

    private interface MockedReceivedMessage extends Transmittable, ClientHandleable {
        // Interface used to represent real messages received by the client
    }

}
