package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;

public abstract class AbstractClientTurnState {
    protected final Client client;

    public AbstractClientTurnState(Client client) {
        this.client = client;
    }

    public abstract void render();

    public abstract void notifyUiInteraction();
}
