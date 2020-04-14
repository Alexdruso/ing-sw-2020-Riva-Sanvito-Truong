package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.messages.ClientJoinLobbyMessage;
import it.polimi.ingsw.utils.messages.ServerMessage;

/**
 * A generic WAIT_PLAYERS ClientState, to be extended by a UI-specific class.
 */
public abstract class AbstractWaitPlayersClientState extends AbstractClientState {
    /**
     * Instantiates a new WAIT_PLAYERS ClientState.
     *
     * @param client the client
     */
    public AbstractWaitPlayersClientState(Client client) {
        super(client);
    }

    @Override
    public void setup() {
        triggerRender();
    }

    @Override
    public void notifyUiInteraction() {
        // No user interaction expected while waiting for players
    }

    @Override
    public boolean handleServerMessage(ServerMessage message) {
        switch (message.getMessageType()) {
            case START_MATCH_SETUP:
                client.moveToState(ClientState.SHOW_GAME_PASSIVE);
                return true;
            default:
                return false;
        }
    }
}
