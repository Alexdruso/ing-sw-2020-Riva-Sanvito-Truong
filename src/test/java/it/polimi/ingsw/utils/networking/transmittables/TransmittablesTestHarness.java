package it.polimi.ingsw.utils.networking.transmittables;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.reducedmodel.ReducedGame;
import it.polimi.ingsw.client.reducedmodel.ReducedPlayer;
import it.polimi.ingsw.client.ui.UI;
import it.polimi.ingsw.server.ServerConnectionSetupHandler;
import it.polimi.ingsw.server.ServerLobbyBuilder;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.User;
import it.polimi.ingsw.server.view.View;
import it.polimi.ingsw.utils.networking.ClientHandleable;
import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.ControllerHandleable;
import it.polimi.ingsw.utils.networking.ServerHandleable;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransmittablesTestHarness {
    private final Transmittable transmittable;
    private final Client mockedClient;
    private final Connection mockedConnection;
    private final Controller mockedController;
    private final String mockedNickname;
    private final ReducedGame mockedReducedGame;
    private final Map<ReducedUser, ReducedPlayer> mockedReducedPlayers;
    private final Set<ReducedUser> mockedReducedUsers;
    private final ServerConnectionSetupHandler mockedServerConnectionSetupHandler;
    private final ServerLobbyBuilder mockedServerLobbyBuilder;
    private final UI mockedUI;
    private final User mockedUser;
    private final View mockedView;

    public TransmittablesTestHarness(Transmittable transmittable) {
        this.transmittable = transmittable;
        mockedClient = mock(Client.class);
        mockedConnection = mock(Connection.class);
        mockedController = mock(Controller.class);
        mockedReducedGame = mock(ReducedGame.class);
        mockedReducedPlayers = new HashMap<>();
        mockedReducedUsers = new HashSet<>();
        mockedNickname = "";
        mockedServerConnectionSetupHandler = mock(ServerConnectionSetupHandler.class);
        mockedServerLobbyBuilder = mock(ServerLobbyBuilder.class);
        mockedUI = mock(UI.class);
        mockedUser = mock(User.class);
        mockedView = mock(View.class);

        when(mockedClient.getGame()).thenReturn(mockedReducedGame);
        when(mockedClient.getUI()).thenReturn(mockedUI);

        when(mockedReducedGame.getPlayer(any(ReducedUser.class))).thenAnswer(mockedCall -> {
            ReducedUser reducedUser = mockedCall.getArgument(0);
            ReducedPlayer ret = mockedReducedPlayers.get(reducedUser);
            if (ret != null) {
                return Optional.of(ret);
            }
            return Optional.empty();
        });

        when(mockedServerConnectionSetupHandler.getConnection()).thenReturn(mockedConnection);
        when(mockedServerConnectionSetupHandler.getLobbyBuilder()).thenReturn(mockedServerLobbyBuilder);
        when(mockedServerConnectionSetupHandler.getNickname()).thenReturn(mockedNickname);
    }

    public ReducedUser registerMockedReducedUser(ReducedUser reducedUser) {
        mockedReducedUsers.add(reducedUser);
        mockedReducedPlayers.put(reducedUser, mock(ReducedPlayer.class));
        return reducedUser;
    }

    public Transmittable getTransmittable() {
        return transmittable;
    }

    public Client getMockedClient() {
        return mockedClient;
    }

    public Connection getMockedConnection() {
        return mockedConnection;
    }

    public Controller getMockedController() {
        return mockedController;
    }

    public String getMockedNickname() {
        return mockedNickname;
    }

    public ReducedGame getMockedReducedGame() {
        return mockedReducedGame;
    }

    public ServerConnectionSetupHandler getMockedServerConnectionSetupHandler() {
        return mockedServerConnectionSetupHandler;
    }

    public ServerLobbyBuilder getMockedServerLobbyBuilder() {
        return mockedServerLobbyBuilder;
    }

    public UI getMockedUI() {
        return mockedUI;
    }

    public User getMockedUser() {
        return mockedUser;
    }

    public View getMockedView() {
        return mockedView;
    }

    public boolean dispatchClient() {
        return ((ClientHandleable) transmittable).handleTransmittable(mockedClient);
    }

    public boolean dispatchController() {
        return ((ControllerHandleable) transmittable).handleTransmittable(mockedController, mockedView, mockedUser);
    }

    public boolean dispatchServerConnectionSetupHandler() {
        return ((ServerHandleable) transmittable).handleTransmittable(mockedServerConnectionSetupHandler);
    }
}
