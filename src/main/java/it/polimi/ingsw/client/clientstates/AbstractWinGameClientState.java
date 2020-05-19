package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;

/**
 * A generic WIN_GAME ClientState, to be extended by a UI-specific class.
 */
public abstract class AbstractWinGameClientState extends AbstractClientState {
    /**
     * Instantiates a new WIN_GAME ClientState.
     *
     * @param client the client
     */
    public AbstractWinGameClientState(Client client) {
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
