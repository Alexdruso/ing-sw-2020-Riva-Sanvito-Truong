package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;

/**
 * A generic LOSE_GAME ClientState, to be extended by a UI-specific class.
 */
public abstract class AbstractLoseGameClientState extends AbstractClientState {
    /**
     * Instantiates a new LOSE_GAME ClientState.
     *
     * @param client the client
     */
    public AbstractLoseGameClientState(Client client) {
        super(client);
    }

    @Override
    public void setup() {
        triggerRender();
    }

    @Override
    public void notifyUiInteraction() {
        client.moveToState(ClientState.DISCONNECT);
    }
}
