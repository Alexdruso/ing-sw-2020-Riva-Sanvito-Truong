package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractSetNicknameClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderBuilder;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import javafx.application.Platform;

/**
 * A GUI-specific SET_NICKNAME client state.
 */
public class SetNicknameGUIClientState extends AbstractSetNicknameClientState implements GUIClientState {
    /**
     * Instantiates a new SET_NICKNAME ClientState.
     *
     * @param client the client
     */
    public SetNicknameGUIClientState(Client client) {
        super(client);
    }

    /**
     * This method sends the client to the menu
     */
    public void returnToMenu(){
        client.moveToState(ClientState.WELCOME_SCREEN);
    }

    /**
     * This method notifies the client that a nickname has been chosen
     * @param nickname the nickname chose by the player
     */
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
        SceneLoaderBuilder sceneLoaderBuilder = new SceneLoaderBuilder("/fxml/SetNickname.fxml", client);
        sceneLoaderBuilder.setState(ClientState.SET_NICKNAME, this).build().executeSceneChange();
    }
}
