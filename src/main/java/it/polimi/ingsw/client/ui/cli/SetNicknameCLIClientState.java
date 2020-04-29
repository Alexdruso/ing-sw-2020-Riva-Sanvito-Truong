package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractSetNicknameClientState;

/**
 * The CLI-specific SET_NICKNAME ClientState.
 */
public class SetNicknameCLIClientState extends AbstractSetNicknameClientState implements CLIClientState {
    private final CLI cli;

    /**
     * Instantiates a new CLI-specific SET_NICKNAME ClientState.
     *
     * @param client the client
     */
    public SetNicknameCLIClientState(Client client) {
        super(client);
        cli = (CLI) client.getUI();
    }

    @Override
    public void render() {
        nickname = cli.readString("Nickname:");
        notifyUiInteraction();
    }

    @Override
    public void handleOk() {
        cli.println(String.format("Benvenuto nel server, %s!", nickname));
        super.handleOk();
    }
}
