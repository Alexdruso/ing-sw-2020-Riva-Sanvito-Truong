package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractDisconnectClientState;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;

/**
 * The CLI-specific DISCONNECT ClientState.
 */
public class DisconnectCLIClientState extends AbstractDisconnectClientState implements CLIClientState {
    private final CLI cli;

    /**
     * Instantiates a new CLI-specific DISCONNECT ClientState.
     *
     * @param client the client
     */
    public DisconnectCLIClientState(Client client) {
        super(client);
        cli = (CLI) client.getUI();
    }

    @Override
    public void render() {
        cli.println(I18n.string(I18nKey.DISCONNECTED_FROM_THE_SERVER));

        if (cli.readYesNo(I18n.string(I18nKey.DO_YOU_WANT_TO_START_A_NEW_MATCH))) {
            notifyUiInteraction();
        }
        else {
            client.requestExit();
        }
    }

}
