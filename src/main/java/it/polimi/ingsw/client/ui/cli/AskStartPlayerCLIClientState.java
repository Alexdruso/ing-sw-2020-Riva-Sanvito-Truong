package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractAskGodFromListClientState;
import it.polimi.ingsw.client.clientstates.AbstractAskStartPlayerClientState;
import it.polimi.ingsw.client.reducedmodel.ReducedPlayer;
import it.polimi.ingsw.utils.messages.ReducedGod;
import it.polimi.ingsw.utils.messages.ReducedUser;

import java.util.ArrayList;
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
        if (client.isCurrentlyActive()) {
            cli.println("I giocatori di questa partita sono:");
            List<ReducedUser> users = client.getGame().getPlayersList().stream().map(ReducedPlayer::getUser).collect(Collectors.toList());
            for (int i = 0; i < users.size(); i++) {
                cli.println(String.format("[%d] %s", i + 1, users.get(i).nickname));
            }

            while (chosenUser == null) {
                int choice = cli.readInt("Scegli il giocatore che iniziera' per primo:") - 1;
                try {
                    chosenUser = users.get(choice);
                } catch (IndexOutOfBoundsException e) {
                    cli.error("Il giocatore indicato non e' valido");
                }
            }

            notifyUiInteraction();
        }
        else {
            cli.println(String.format("Attendi che %s scelga il giocatore che iniziera' la partita...", client.getCurrentActiveUser().nickname));
        }
    }
}
