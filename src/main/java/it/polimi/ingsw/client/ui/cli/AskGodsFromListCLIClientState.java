package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractAskGodsFromListClientState;
import it.polimi.ingsw.utils.messages.ReducedGod;

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
        for (ReducedGod god : client.getGods()) {
            cli.println(god.name);
        }
    }

    @Override
    public void notifyUiInteraction() {

    }
}
