package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractAskGodFromListClientState;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import it.polimi.ingsw.utils.networking.transmittables.ReducedGod;

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
            List<ReducedGod> gods = new ArrayList<>(client.getGodsAvailableForChoice());

            cli.println("");
            while (chosenGod == null) {
                cli.clear();
                cli.println(String.format("%s:", I18n.string(I18nKey.YOU_CAN_CHOOSE_ONE_OF_THESE_GODS)));
                for (int i = 0; i < gods.size(); i++) {
                    cli.println(String.format("[%d] %s", i + 1, cli.getGodNameAndSubtitle(gods.get(i))));
                }
                int choice = cli.readInt(String.format("%s:", I18n.string(I18nKey.CHOOSE_YOUR_GOD))) - 1;
                try {
                    chosenGod = gods.get(choice);
                    boolean choiceConfirmed = cli.printGodCardConfirmationScreen(chosenGod);
                    if (!choiceConfirmed) {
                        chosenGod = null;
                    }
                } catch (IndexOutOfBoundsException e) {
                    cli.error(I18n.string(I18nKey.THE_CHOSEN_GOD_IS_INVALID));
                }
            }

            notifyUiInteraction();
        }
        else {
            cli.println(
                    String.format(
                            I18n.string(I18nKey.WAIT_FOR_S_TO_CHOOSE_THEIR_GOD),
                            client.getCurrentActiveUser().getNickname()
                    )
            );
        }
    }
}
