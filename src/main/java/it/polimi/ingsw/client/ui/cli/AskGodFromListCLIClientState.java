package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractAskGodFromListClientState;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import it.polimi.ingsw.utils.messages.ReducedGod;

import java.util.ArrayList;
import java.util.List;

public class AskGodFromListCLIClientState extends AbstractAskGodFromListClientState {
    private final CLI cli;

    /**
     * Instantiates a new CLI-specific ASK_GOD_FROM_LIST ClientState.
     *
     * @param client the client
     */
    public AskGodFromListCLIClientState(Client client) {
        super(client);
        cli = (CLI) client.getUI();
    }

    @Override
    public void render() {
        cli.clear();
        if (client.isCurrentlyActive()) {
            cli.println(String.format("%s:", I18n.string(I18nKey.YOU_CAN_CHOOSE_ONE_OF_THESE_GODS)));
            List<ReducedGod> gods = new ArrayList<>(client.getGods());
            for (int i = 0; i < gods.size(); i++) {
                cli.println(String.format("[%d] %s: %s", i + 1, I18n.string(I18nKey.valueOf(String.format("%s_NAME", gods.get(i).name.toUpperCase()))), I18n.string(I18nKey.valueOf(String.format("%s_SUBTITLE", gods.get(i).name.toUpperCase())))));
            }

            cli.println("");
            while (chosenGod == null) {
                int choice = cli.readInt(String.format("%s:", I18n.string(I18nKey.CHOOSE_YOUR_GOD))) - 1;
                try {
                    chosenGod = gods.get(choice);
                } catch (IndexOutOfBoundsException e) {
                    cli.error(I18n.string(I18nKey.THE_CHOSEN_GOD_IS_INVALID));
                }
            }

            notifyUiInteraction();
        }
        else {
            cli.println(String.format(I18n.string(I18nKey.WAIT_FOR_S_TO_CHOOSE_THEIR_GOD), client.getCurrentActiveUser().nickname));
        }
    }
}
