package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractLoseGameClientState;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The CLI-specific LOSE_GAME ClientState.
 */
public class LoseGameCLIClientState extends AbstractLoseGameClientState implements CLIClientState {
    private final CLI cli;

    /**
     * Instantiates a new CLI-specific LOSE_GAME ClientState.
     *
     * @param client the client
     */
    public LoseGameCLIClientState(Client client) {
        super(client);
        cli = (CLI) client.getUI();
    }

    @Override
    public void render() {
        AtomicBoolean showLoseScreen = new AtomicBoolean(false);
        client.getGame().getPlayer(client.getNickname()).ifPresent(
                player -> {
                    if (!player.isInGame()) {
                        showLoseScreen.set(true);
                    }
                }
        );
        if (showLoseScreen.get()) {
            cli.clear();
            cli.println("\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0,\u2553\u2553\u2554\u2554\u2554\u2554\u2554\u2554\u2554\u2553,\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\r\n\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0,\u2554\u03C6\u256C\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2592\u2592\u2554,\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\r\n\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u2554\u2560\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u256C\u2265\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\r\n\u00A0\u00A0\u00A0\u00A0\u00A0\u2554\u256C\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2566\u00A0\u00A0\u00A0\u00A0\u00A0\r\n\u00A0\u00A0\u00A0\u00A0\u03C6\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2560\u00A0\u00A0\u00A0\u00A0\r\n\u00A0\u00A0\u00A0\u2560\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2592\u00A0\u00A0\u00A0\r\n\u00A0\u00A0\u2560\u2591\u2591\u2591\u2591\u2591\u2590\u2588\u2584\u2591\u2591\u2591\u2591\u2584\u2588\u258C\u2591\u2591\u2591\u2591\u2590\u2588\u2584\u2591\u2591\u2591\u2591\u2584\u2588\u258C\u2591\u2591\u2591\u2591\u2591\u2560\u00A0\u00A0\r\n\u00A0\u2554\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2559\u2580\u2588\u2588\u2588\u2588\u2580\u2580\u2591\u2591\u2591\u2591\u2591\u2591\u2559\u2580\u2588\u2588\u2588\u2588\u2580\u2580\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2592\u00A0\r\n\u00A0\u2592\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u00A0\r\n]\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u03B5\r\n\u2554\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2592\r\n\u255A\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2580\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\r\n\u255A\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u256C\r\n\u00A0\"\u256C\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2592\"\u00A0\r\n\u00A0\u00A0\u00A0\u00A0\"\u2559\u2560\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u2591\u256C\u255A\"\u00A0\u00A0\u00A0\u00A0\r\n\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0`\"\"\u207F\u2559\u255A\u255A\u255A\u2569\u2569\u2569\u2569\u255A\u255A\u255A\u2559\u207F\"\"`\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0");
            cli.println(I18n.string(I18nKey.YOU_LOST));
            boolean keepWatching = cli.readYesNo(I18n.string(I18nKey.DO_YOU_WANT_TO_KEEP_WATCHING_THE_GAME));
            if (!keepWatching) {
                notifyUiInteraction();
            }
        }
    }
}
