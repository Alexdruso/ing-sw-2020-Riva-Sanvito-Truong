package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractAskStartPlayerClientState;
import it.polimi.ingsw.client.reducedmodel.ReducedPlayer;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;

import java.util.List;
import java.util.stream.Collectors;

public class AskStartPlayerCLIClientState extends AbstractAskStartPlayerClientState {
    private final CLI cli;

    /**
     * Instantiates a new CLI-specific ASK_START_PLAYER ClientState.
     *
     * @param client the client
     */
    public AskStartPlayerCLIClientState(Client client) {
        super(client);
        cli = (CLI) client.getUI();
    }

    @Override
    public void render() {
        cli.clear();
        if (client.isCurrentlyActive()) {
            cli.println(String.format("%s:", I18n.string(I18nKey.THE_PLAYERS_FOR_THIS_MATCH_ARE)));
            List<ReducedUser> users = client.getGame().getPlayersList().stream().map(ReducedPlayer::getUser).collect(Collectors.toList());
            for (int i = 0; i < users.size(); i++) {
                cli.println(String.format("[%d] %s", i + 1, users.get(i).getNickname()));
            }

            cli.println("");
            while (chosenUser == null) {
                int choice = cli.readInt(String.format("%s:", I18n.string(I18nKey.CHOOSE_THE_PLAYER_THAT_WILL_START_FIRST))) - 1;
                try {
                    chosenUser = users.get(choice);
                } catch (IndexOutOfBoundsException e) {
                    cli.error(I18n.string(I18nKey.THE_CHOSEN_PLAYER_IS_INVALID));
                }
            }

            notifyUiInteraction();
        }
        else {
            cli.println(
                    String.format(
                            I18n.string(I18nKey.WAIT_FOR_S_TO_CHOOSE_THE_STARTING_PLAYER),
                            client.getCurrentActiveUser().getNickname()
                    )
            );
        }
    }
}
