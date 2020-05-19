package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractWinGameClientState;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;

/**
 * The CLI-specific WIN_GAME ClientState.
 */
public class WinGameCLIClientState extends AbstractWinGameClientState implements CLIClientState {
    private final CLI cli;

    /**
     * Instantiates a new CLI-specific WIN_GAME ClientState.
     *
     * @param client the client
     */
    public WinGameCLIClientState(Client client) {
        super(client);
        cli = (CLI) client.getUI();
    }

    @Override
    public void render() {
        cli.clear();
        if (client.isCurrentlyActive()) {
            cli.println(I18n.string(I18nKey.CONGRATULATIONS_YOU_WON));
        }
        else {
            cli.println(String.format(I18n.string(I18nKey.S_WON), client.getCurrentActiveUser().nickname));
        }
        cli.pause();
        notifyUiInteraction();
    }
}
