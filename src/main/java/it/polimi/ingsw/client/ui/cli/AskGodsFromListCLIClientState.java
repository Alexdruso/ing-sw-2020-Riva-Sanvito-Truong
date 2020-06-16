package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractAskGodsFromListClientState;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import it.polimi.ingsw.utils.networking.transmittables.ReducedGod;

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
            List<ReducedGod> gods = new ArrayList<>(client.getGods());

            while (chosenGods.size() < playersCount) {
                cli.clear();
                cli.println(String.format(String.format("%s:", I18n.string(I18nKey.CHOOSE_D_GODS_THAT_WILL_BE_AVAILABLE)), playersCount));
                for (int i = 0; i < gods.size(); i++) {
                    cli.println(String.format("[%02d] %s", i + 1, cli.getGodNameAndSubtitle(gods.get(i))));
                }
                if (!chosenGods.isEmpty()) {
                    cli.println("");
                    cli.println(String.format("%s:", I18n.string(I18nKey.GODS_ALREADY_CHOSEN)));
                    for (ReducedGod chosenGod : chosenGods) {
                        cli.println(String.format("- %s", cli.getGodNameAndSubtitle(chosenGod)));
                    }
                }
                cli.println("");

                int choice = cli.readInt(String.format(String.format("%s:", I18n.string(I18nKey.CHOOSE_THE_GOD_D)), chosenGods.size() + 1)) - 1;
                try {
                    ReducedGod chosenGod = gods.get(choice);
                    boolean choiceConfirmed = cli.printGodCardConfirmationScreen(chosenGod);
                    if (choiceConfirmed) {
                        chosenGods.add(chosenGod);
                        gods.remove(choice);
                    }
                }
                catch (IndexOutOfBoundsException e) {
                    cli.error(I18n.string(I18nKey.THE_CHOSEN_GOD_IS_INVALID));
                }
            }

            notifyUiInteraction();
        }
        else {
            cli.println(String.format(I18n.string(I18nKey.WAIT_FOR_S_TO_CHOOSE_THE_GODS), client.getCurrentActiveUser().nickname));
        }
    }
}
