package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractConnectToServerClientState;
import it.polimi.ingsw.client.clientstates.AbstractWelcomeScreenState;
import it.polimi.ingsw.config.ConfigParser;

/**
 * The CLI-specific WELCOME_SCREEN ClientState.
 */
public class WelcomeScreenCLIClientState extends AbstractWelcomeScreenState implements CLIClientState {
    private final CLI cli;

    /**
     * Instantiates a new CLI-specific WELCOME_SCREEN ClientState.
     *
     * @param client the client
     */
    public WelcomeScreenCLIClientState(Client client) {
        super(client);
        cli = (CLI) client.getUI();
    }

    @Override
    public void render() {
        cli.clear();
        cli.println("\n" +
                "███████╗ █████╗ ███╗   ██╗████████╗ ██████╗ ██████╗ ██╗███╗   ██╗██╗\n" +
                "██╔════╝██╔══██╗████╗  ██║╚══██╔══╝██╔═══██╗██╔══██╗██║████╗  ██║██║\n" +
                "███████╗███████║██╔██╗ ██║   ██║   ██║   ██║██████╔╝██║██╔██╗ ██║██║\n" +
                "╚════██║██╔══██║██║╚██╗██║   ██║   ██║   ██║██╔══██╗██║██║╚██╗██║██║\n" +
                "███████║██║  ██║██║ ╚████║   ██║   ╚██████╔╝██║  ██║██║██║ ╚████║██║\n" +
                "╚══════╝╚═╝  ╚═╝╚═╝  ╚═══╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝╚═╝\n" +
                "                                                                    \n" +
                "                              by GC02                               \n" +
                "                  A. Riva, A. Sanvito, K. T. Truong                 \n" +
                "\n");
        cli.readString(
                "               --- Premi 'Invio' per iniziare... ---", null, 0);
        notifyUiInteraction();
    }
}
