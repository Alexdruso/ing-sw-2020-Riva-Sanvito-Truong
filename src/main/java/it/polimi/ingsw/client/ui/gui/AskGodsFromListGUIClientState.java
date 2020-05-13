package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.JavaFXApp;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractAskGodsFromListClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoader;
import it.polimi.ingsw.utils.messages.ReducedGod;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AskGodsFromListGUIClientState extends AbstractAskGodsFromListClientState implements GUIClientState{
    private final GUI gui;
    private final Scene mainScene;
    private final Stage primaryStage;
    private int selectedCount = 0;
    private int playersCount;
    /**
     * Instantiates a new ClientState.
     *
     * @param client the client
     */
    public AskGodsFromListGUIClientState(Client client) {
        super(client);
        gui = (GUI)client.getUI();
        primaryStage = JavaFXApp.getPrimaryStage();
        mainScene = JavaFXApp.getPrimaryScene();
        playersCount = client.getGame().getPlayersCount();
    }

    public void returnToMenu(){
        client.moveToState(ClientState.WELCOME_SCREEN);
    }

    public void addChosenGod(ReducedGod god){
        chosenGods.add(god);
        selectedCount++;
        if(selectedCount == playersCount){
            notifyUiInteraction();
        }
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
        if(client.isCurrentlyActive()){
            mainScene.getStylesheets().add(getClass().getResource("/css/god-selection.css").toExternalForm());
            SceneLoader.loadFromFXML("/fxml/AskGodsFromList.fxml", mainScene, client, this, ClientState.ASK_GODS_FROM_LIST, true, false);
        } else {
            SceneLoader.loadFromFXML("/fxml/AskGodsFromListPassive.fxml", mainScene, client, this, ClientState.ASK_GODS_FROM_LIST, true, false);
        }
    }
}
