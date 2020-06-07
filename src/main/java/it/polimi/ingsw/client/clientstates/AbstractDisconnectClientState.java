package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;

/**
 * A generic DISCONNECT ClientState, to be extended by a UI-specific class.
 */
public abstract class AbstractDisconnectClientState extends AbstractClientState {
    /**
     * Instantiates a new DISCONNECT ClientState.
     *
     * @param client the client
     */
    public AbstractDisconnectClientState(Client client) {
        super(client);
    }

    @Override
    public void setup() {
        client.closeConnection();
        triggerRender();
    }

    @Override
    public void notifyUiInteraction() {
        client.moveToState(ClientState.CONNECT_TO_SERVER);
    }
}
