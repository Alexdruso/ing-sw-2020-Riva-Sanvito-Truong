package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractSetNicknameClientState;
import it.polimi.ingsw.utils.config.ConfigParser;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;

/**
 * The CLI-specific SET_NICKNAME ClientState.
 */
public class SetNicknameCLIClientState extends AbstractSetNicknameClientState implements CLIClientState {
    private final CLI cli;

    /**
     * Instantiates a new CLI-specific SET_NICKNAME ClientState.
     *
     * @param client the client
     */
    public SetNicknameCLIClientState(Client client) {
        super(client);
        cli = (CLI) client.getUI();
    }

    @Override
    public void render() {
        do {
            nickname = cli.readString(String.format("%s:", I18n.string(I18nKey.NICKNAME)));
            if (nickname.length() == 0) {
                cli.error(I18n.string(I18nKey.ERROR_INVALID_NICKNAME_GENERIC));
                nickname = null;
            }
            else if (nickname.length() >= ConfigParser.getInstance().getIntProperty("nicknameMaxLength")) {
                cli.error(I18n.string(I18nKey.ERROR_INVALID_NICKNAME_LENGTH));
                nickname = null;
            }
        } while (nickname == null);
        notifyUiInteraction();
    }

    @Override
    public void handleClientError() {
        cli.error(I18n.string(I18nKey.ERROR_NICKNAME_TAKEN));
        super.handleClientError();
    }

    @Override
    public void handleOk() {
        cli.clear();
        cli.println(String.format(I18n.string(I18nKey.WELCOME_TO_THE_SERVER_S), nickname));
        super.handleOk();
    }
}
