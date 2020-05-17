package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.JavaFXApp;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractAskGodsFromListClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderFactory;
import it.polimi.ingsw.utils.messages.ReducedGod;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AskGodsFromListGUIClientState extends AbstractAskGodsFromListClientState implements GUIClientState{
    private int selectedCount = 0;
    private int playersCount;
    /**
     * Instantiates a new ClientState.
     *
     * @param client the client
     */
    public AskGodsFromListGUIClientState(Client client) {
        super(client);
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
        SceneLoaderFactory sceneLoaderFactory;
        if(client.isCurrentlyActive()){
            sceneLoaderFactory = new SceneLoaderFactory("/fxml/AskGodsFromList.fxml", client);
        } else {
            sceneLoaderFactory = new SceneLoaderFactory("/fxml/AskGodsFromListPassive.fxml", client);
        }
        sceneLoaderFactory.addCSSFile("/css/god-selection.css")
                .setState(ClientState.ASK_GODS_FROM_LIST, this)
                .build()
                .executeSceneChange();
    }
}
