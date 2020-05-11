package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.messages.ClientSetPlayersCountMessage;

/**
 * A generic SET_PLAYERS_COUNT ClientState, to be extended by a UI-specific class.
 */
public abstract class AbstractSetPlayersCountClientState extends AbstractClientState {
    /**
     * The players count.
     */
    protected int playersCount;

    /**
     * Instantiates a new SET_PLAYERS_COUNT ClientState.
     *
     * @param client the client
     */
    public AbstractSetPlayersCountClientState(Client client) {
        super(client);
    }

    @Override
    public void setup() {
        triggerRender();
    }

    @Override
    public void notifyUiInteraction() {
        client.getConnection().send(new ClientSetPlayersCountMessage(playersCount));
    }

    @Override
    public void handleClientError() {
        client.getUI().notifyError("Il numero di giocatori specificato non e' valido.");
        client.moveToState(ClientState.SET_PLAYERS_COUNT);
    }

    @Override
    public void handleOk() {
        client.moveToState(ClientState.WAIT_PLAYERS);
    }
}