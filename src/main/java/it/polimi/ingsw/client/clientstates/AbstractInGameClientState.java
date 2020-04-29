package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.messages.ServerMessage;

/**
 * A generic  ClientState, to be extended by a UI-specific class.
 */
public abstract class AbstractInGameClientState extends AbstractClientState {
    /**
     * Instantiates a new  ClientState.
     *
     * @param client the client
     */
    public AbstractInGameClientState(Client client) {
        super(client);
    }

    @Override
    public void setup() {
        triggerRender();
    }

    @Override
    public boolean handleServerMessage(ServerMessage message) {
        return false;
    }
}
