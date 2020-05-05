package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractConnectToServerClientState;
import it.polimi.ingsw.config.ConfigParser;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;

/**
 * The CLI-specific CONNECT_TO_SERVER ClientState.
 */
public class ConnectToServerCLIClientState extends AbstractConnectToServerClientState implements CLIClientState {
    private final CLI cli;

    /**
     * Instantiates a new CLI-specific CONNECT_TO_SERVER ClientState.
     *
     * @param client the client
     */
    public ConnectToServerCLIClientState(Client client) {
        super(client);
        cli = (CLI) client.getUI();
    }

    @Override
    public void render() {
        cli.clear();
        host = cli.readString(String.format("%s:", I18n.string(I18nKey.SERVER_ADDRESS)), "127.0.0.1");
        port = cli.readInt(String.format("%s:", I18n.string(I18nKey.PORT)), ConfigParser.getInstance().getIntProperty("serverPort"));
        notifyUiInteraction();
    }


}
