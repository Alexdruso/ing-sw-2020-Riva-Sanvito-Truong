package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;

public abstract class AbstractPassiveClientState extends AbstractClientTurnState {
    public AbstractPassiveClientState(Client client) {
        super(client);
    }
}
