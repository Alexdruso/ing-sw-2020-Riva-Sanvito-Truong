package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractAskGodFromListClientState;
import it.polimi.ingsw.client.clientstates.AbstractAskGodsFromListClientState;
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
        if (client.isCurrentlyActive()) {
            cli.println("Puoi scegliere una di queste divinita' con cui giocare questa partita:");
            List<ReducedGod> gods = new ArrayList<>(client.getGods());
            for (int i = 0; i < gods.size(); i++) {
                cli.println(String.format("[%d] %s", i + 1, gods.get(i).name));
            }

            while (chosenGod == null) {
                int choice = cli.readInt("Scegli la tua divinita':") - 1;
                try {
                    chosenGod = gods.get(choice);
                } catch (IndexOutOfBoundsException e) {
                    cli.error("La divinta' indicata non e' valida");
                }
            }

            notifyUiInteraction();
        }
        else {
            cli.println(String.format("Attendi che %s scelga la divinita' con cui giocare questa partita...", client.getCurrentActiveUser().nickname));
        }
    }
}
