package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.messages.ClientJoinLobbyMessage;
import it.polimi.ingsw.utils.messages.ServerMessage;

/**
 * A generic SHOW_GAME_PASSIVE ClientState, to be extended by a UI-specific class.
 */
public abstract class AbstractShowGamePassiveClientState extends AbstractClientState {
    /**
     * Instantiates a new SHOW_GAME_PASSIVE ClientState.
     *
     * @param client the client
     */
    public AbstractShowGamePassiveClientState(Client client) {
        super(client);
    }

    @Override
    public void setup() {
        triggerRender();
    }

    @Override
    public void notifyUiInteraction() {
        // No user interaction expected while in passive mode
    }

    @Override
    public boolean handleServerMessage(ServerMessage message) {
        return false;
    }
}
