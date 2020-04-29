package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;

public interface ClientTurnState {
    void render(AbstractClientState clientState, Client client);
}
