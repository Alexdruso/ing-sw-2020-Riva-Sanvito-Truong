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


    @Override
    public void render() {
        SceneLoaderFactory sceneLoaderFactory = new SceneLoaderFactory("/fxml/SetNickname.fxml", client);
        sceneLoaderFactory.setState(ClientState.SET_NICKNAME, this).build().executeSceneChange();
    }
}
