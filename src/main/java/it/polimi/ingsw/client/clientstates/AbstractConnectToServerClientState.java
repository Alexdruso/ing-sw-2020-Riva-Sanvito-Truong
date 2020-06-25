package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import it.polimi.ingsw.utils.networking.Connection;

import java.io.IOException;

/**
 * A generic CONNECT_TO_SERVER ClientState, to be extended by a UI-specific class.
 */
public abstract class AbstractConnectToServerClientState extends AbstractClientState {
    /**
     * The server host address.
     */
    protected String host;
    /**
     * The server port.
     */
    protected int port;

    /**
     * Instantiates a new CONNECT_TO_SERVER ClientState.
     *
     * @param client the client
     */
    public AbstractConnectToServerClientState(Client client) {
        super(client);
    }

    @Override
    public void setup() {
        triggerRender();
    }

    @Override
    public void notifyUiInteraction() {
        try {
            Connection connection = new Connection(host, port);
            client.setConnection(connection);
            client.moveToState(ClientState.SET_NICKNAME);
        } catch (IOException e) {
            client.getUI().notifyError(I18n.string(I18nKey.ERROR_CANNOT_CONNECT), e);
            client.moveToState(ClientState.CONNECT_TO_SERVER);
        }
    }


}
