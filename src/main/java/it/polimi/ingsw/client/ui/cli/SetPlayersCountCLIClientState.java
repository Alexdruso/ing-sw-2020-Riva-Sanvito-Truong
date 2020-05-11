package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractSetPlayersCountClientState;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;

/**
 * The CLI-specific SET_PLAYERS_COUNT ClientState.
 */
public class SetPlayersCountCLIClientState extends AbstractSetPlayersCountClientState implements CLIClientState {
    private final CLI cli;

    /**
     * Instantiates a new CLI-specific SET_PLAYERS_COUNT ClientState.
     *
     * @param client the client
     */
    public SetPlayersCountCLIClientState(Client client) {
        super(client);
        cli = (CLI) client.getUI();
    }

    @Override
    public void render() {
        cli.clear();
        cli.println(I18n.string(I18nKey.YOU_ARE_THE_FIRST_PLAYER_IN_THE_SERVER));
        playersCount = cli.readInt(String.format("%s [2-3]:", I18n.string(I18nKey.NUMBER_OF_PLAYERS)));
        notifyUiInteraction();
    }
}
