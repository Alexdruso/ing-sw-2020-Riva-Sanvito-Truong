package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.messages.ClientSetNicknameMessage;

/**
 * A generic SET_NICKNAME CLientState, to be extended by a UI-specific class.
 */
public abstract class AbstractSetNicknameClientState extends AbstractClientState {
    /**
     * The nickname.
     */
    protected String nickname;

    /**
     * Instantiates a new SET_NICKNAME CLientState.
     *
     * @param client the client
     */
    public AbstractSetNicknameClientState(Client client) {
        super(client);
    }

    @Override
    public void setup() {
        triggerRender();
    }

    @Override
    public void notifyUiInteraction() {
        client.getConnection().send(new ClientSetNicknameMessage(nickname));
    }

    @Override
    public void handleClientError() {
        client.getUi().notifyError("Il nickname scelto e' gia' in uso.");
        client.moveToState(ClientState.SET_NICKNAME);
    }

    @Override
    public void handleOk() {
        client.setNickname(nickname);
        client.moveToState(ClientState.JOIN_LOBBY);
    }
}