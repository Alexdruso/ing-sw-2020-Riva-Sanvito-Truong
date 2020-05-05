package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractAskGodsFromListClientState;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import it.polimi.ingsw.utils.messages.ReducedGod;

import java.util.ArrayList;
import java.util.List;

public class AskGodsFromListCLIClientState extends AbstractAskGodsFromListClientState {
    private final CLI cli;

    /**
     * Instantiates a new CLI-specific CONNECT_TO_SERVER ClientState.
     *
     * @param client the client
     */
    public AskGodsFromListCLIClientState(Client client) {
        super(client);
        cli = (CLI) client.getUI();
    }

    @Override
    public void render() {
        cli.clear();
        if (client.isCurrentlyActive()) {
            int playersCount = client.getGame().getPlayersCount();
            cli.println(String.format(String.format("%s:", I18n.string(I18nKey.CHOOSE_D_GODS_THAT_WILL_BE_AVAILABLE)), playersCount));
            List<ReducedGod> gods = new ArrayList<>(client.getGods());
            for (int i = 0; i < gods.size(); i++) {
                cli.println(String.format("[%02d] %s", i + 1, gods.get(i).name));
            }

            cli.println("");
            while (chosenGods.size() < playersCount) {
                int choice = cli.readInt(String.format(String.format("%s:", I18n.string(I18nKey.CHOOSE_THE_GOD_D)), chosenGods.size() + 1)) - 1;
                try {
                    ReducedGod chosenGod = gods.get(choice);
                    if (chosenGod != null) {
                        chosenGods.add(chosenGod);
                        gods.set(choice, null);
                    }
                    else {
                        cli.error(I18n.string(I18nKey.THE_CHOSEN_GOD_IS_INVALID));
                    }
                }
                catch (IndexOutOfBoundsException e) {
                    cli.error(I18n.string(I18nKey.THE_CHOSEN_GOD_WAS_ALREADY_TAKEN));
                }
            }

            notifyUiInteraction();
        }
        else {
            cli.println(String.format(I18n.string(I18nKey.WAIT_FOR_S_TO_CHOOSE_THE_GODS), client.getCurrentActiveUser().nickname));
        }
    }
}
