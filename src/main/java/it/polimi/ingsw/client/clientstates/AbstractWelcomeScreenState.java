package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;

public abstract class AbstractWelcomeScreenState extends AbstractClientState{
    /**
     * Instantiates a new ClientState.
     *
     * @param client the client
     */
    public AbstractWelcomeScreenState(Client client) {
        super(client);
    }

    @Override
    public void setup() {
        triggerRender();
    }

    @Override
    public void notifyUiInteraction() {
        client.moveToState(ClientState.CONNECT_TO_SERVER);
    }
}
