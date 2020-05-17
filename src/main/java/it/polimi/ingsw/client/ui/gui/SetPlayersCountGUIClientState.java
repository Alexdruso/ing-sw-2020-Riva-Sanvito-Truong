package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.JavaFXApp;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractSetPlayersCountClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoader;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderFactory;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SetPlayersCountGUIClientState extends AbstractSetPlayersCountClientState implements GUIClientState {
    private final GUI gui;
    private final Stage primaryStage;
    private final Scene mainScene;
    /**
     * Instantiates a new SET_PLAYERS_COUNT ClientState.
     *
     * @param client the client
     */
    public SetPlayersCountGUIClientState(Client client) {
        super(client);
        gui = (GUI)client.getUI();
        primaryStage = JavaFXApp.getPrimaryStage();
        mainScene = primaryStage.getScene();
    }

    public void setPlayersCount(int playersCount){
        this.playersCount = playersCount;
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
        SceneLoaderFactory sceneLoaderFactory = new SceneLoaderFactory("/fxml/SetPlayersCount.fxml", client);
        sceneLoaderFactory.setState(ClientState.SET_PLAYERS_COUNT, this).build().executeSceneChange();
    }
}
