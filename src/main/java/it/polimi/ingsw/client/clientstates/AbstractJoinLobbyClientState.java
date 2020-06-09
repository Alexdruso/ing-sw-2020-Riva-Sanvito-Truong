package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.networking.transmittables.clientmessages.ClientJoinLobbyMessage;

/**
 * A generic JOIN_LOBBY ClientState, to be extended by a UI-specific class.
 */
public abstract class AbstractJoinLobbyClientState extends AbstractClientState {
    /**
     * Instantiates a new JOIN_LOBBY ClientState.
     *
     * @param client the client
     */
    public AbstractJoinLobbyClientState(Client client) {
        super(client);
    }

    @Override
    public void setup() {
        client.getConnection().send(new ClientJoinLobbyMessage());
        triggerRender();
    }

    @Override
    public void notifyUiInteraction() {
        // No user interaction expected while waiting for match
    }

    @Override
    public void handleContinue() {
        client.moveToState(ClientState.SET_PLAYERS_COUNT);
    }
}
