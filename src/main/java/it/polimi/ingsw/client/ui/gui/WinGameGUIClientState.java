package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractSetNicknameClientState;
import it.polimi.ingsw.client.clientstates.AbstractWinGameClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderFactory;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import javafx.application.Platform;

public class WinGameGUIClientState extends AbstractWinGameClientState implements GUIClientState {
    /**
     * Instantiates a new WIN_GAME ClientState.
     *
     * @param client the client
     */
    public WinGameGUIClientState(Client client) {
        super(client);
    }

    @Override
    public void render() {
        //TODO
    }


}
