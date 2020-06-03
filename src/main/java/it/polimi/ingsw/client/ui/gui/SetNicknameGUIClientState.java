package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractSetNicknameClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderFactory;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import javafx.application.Platform;

public class SetNicknameGUIClientState extends AbstractSetNicknameClientState implements GUIClientState {
    /**
     * Instantiates a new SET_NICKNAME ClientState.
     *
     * @param client the client
     */
    public SetNicknameGUIClientState(Client client) {
        super(client);
    }

    public void returnToMenu(){
        client.moveToState(ClientState.WELCOME_SCREEN);
        client.closeConnection();
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
        notifyUiInteraction();
    }

    @Override
    public void handleClientError() {
        Platform.runLater(() -> client.getUI().notifyError(I18n.string(I18nKey.ERROR_NICKNAME_TAKEN)));
        super.handleClientError();
    }

    /**
     * Function called by the main thread that renders the current state to the UI.
     * This function is the only one of this class allowed to be synchronous with the user input.
     * Please, be aware that calls to this function must be either:
     * - guaranteed not to happen concurrently with a Client state change
     * - or the implementation of this function must be self-sufficient (i.e., it does not depend on calls of render of previous states)
     */
    @Override
    public void render() {
        SceneLoaderFactory sceneLoaderFactory = new SceneLoaderFactory("/fxml/SetNickname.fxml", client);
        sceneLoaderFactory.setState(ClientState.SET_NICKNAME, this).build().executeSceneChange();
    }
}
