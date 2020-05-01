package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractAskGodsFromListClientState;
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
            cli.println(String.format("Scegli le %d divinita' che saranno disponibili per questa partita:", playersCount));
            List<ReducedGod> gods = new ArrayList<>(client.getGods());
            for (int i = 0; i < gods.size(); i++) {
                cli.println(String.format("[%02d] %s", i + 1, gods.get(i).name));
            }

            cli.println("");
            while (chosenGods.size() < playersCount) {
                int choice = cli.readInt(String.format("Scegli la %d^ divinita':", chosenGods.size() + 1)) - 1;
                try {
                    ReducedGod chosenGod = gods.get(choice);
                    if (chosenGod != null) {
                        chosenGods.add(chosenGod);
                        gods.set(choice, null);
                    }
                    else {
                        cli.error("La divinta' indicata e' gia' stata scelta");
                    }
                }
                catch (IndexOutOfBoundsException e) {
                    cli.error("La divinta' indicata non e' valida");
                }
            }

            notifyUiInteraction();
        }
        else {
            cli.println(String.format("Attendi che %s scelga le divinita' che saranno disponibili per questa partita...", client.getCurrentActiveUser().nickname));
        }
    }
}
