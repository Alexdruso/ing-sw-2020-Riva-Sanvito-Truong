package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractSetNicknameClientState;

/**
 * The CLI-specific SET_NICKNAME ClientState.
 */
public class SetNicknameCliClientState extends AbstractSetNicknameClientState {
    private final Cli cli;

    /**
     * Instantiates a new CLI-specific SET_NICKNAME ClientState.
     *
     * @param client the client
     */
    public SetNicknameCliClientState(Client client) {
        super(client);
        cli = (Cli) client.getUi();
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
