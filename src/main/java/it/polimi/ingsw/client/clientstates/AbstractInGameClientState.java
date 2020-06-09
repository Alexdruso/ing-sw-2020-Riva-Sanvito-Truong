package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.networking.transmittables.servermessages.ServerMessage;

/**
 * A generic  ClientState, to be extended by a UI-specific class.
 */
public abstract class AbstractInGameClientState extends AbstractClientState {
    /**
     * Instantiates a new IN_GAME ClientState.
     *
     * @param client the client
     */
    public AbstractInGameClientState(Client client) {
        super(client);
    }

    @Override
    public void setup() {
        // triggerRender should be triggered by the ClientTurnStates, after they performed the actions they need.
    }

    @Override
    public void notifyUiInteraction() {
        client.getGame().getTurn().getTurnState().notifyUiInteraction();
    }

    @Override
    public boolean handleServerMessage(ServerMessage message) {
        return false;
    }

    @Override
    public void handleClientError() throws UnsupportedOperationException {
        client.getGame().getTurn().getTurnState().handleClientError();
        client.moveToState(ClientState.IN_GAME);
        triggerRender();
    }
}
