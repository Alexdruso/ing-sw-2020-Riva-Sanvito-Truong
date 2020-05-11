package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.JavaFXApp;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractSetNicknameClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class SetNicknameGUIClientState extends AbstractSetNicknameClientState implements GUIClientState {
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());
    private final GUI gui;
    private final Stage primaryStage;
    private final Scene mainScene;

    /**
     * Instantiates a new SET_NICKNAME ClientState.
     *
     * @param client the client
     */
    public SetNicknameGUIClientState(Client client) {
        super(client);
        gui = (GUI)client.getUI();
        primaryStage = JavaFXApp.getPrimaryStage();
        mainScene = primaryStage.getScene();
    }

    public void returnToMenu(){
        client.moveToState(ClientState.WELCOME_SCREEN);
        client.closeConnection();
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
        notifyUiInteraction();
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
        SceneLoader.loadFromFXML("/fxml/SetNickname.fxml", mainScene, client, this, ClientState.SET_NICKNAME, true);
    }

    @Override
    public void handleOk(){
        super.handleOk();
    }
}
