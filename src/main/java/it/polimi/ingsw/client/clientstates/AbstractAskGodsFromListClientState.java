package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;

public abstract class AbstractAskGodsFromListClientState extends AbstractClientState{
    /**
     * Instantiates a new ClientState.
     *
     * @param client the client
     */
    public AbstractAskGodsFromListClientState(Client client) {
        super(client);
    }

    @Override
    public void setup() {
        triggerRender();
    }
}
