package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.JavaFXApp;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractAskGodFromListClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoader;
import it.polimi.ingsw.utils.messages.ReducedGod;
import javafx.scene.Scene;

public class AskGodFromListGUIClientState extends AbstractAskGodFromListClientState implements GUIClientState{
    private final GUI gui;
    private final Scene mainScene;
    /**
     * Instantiates a new ClientState.
     *
     * @param client the client
     */
    public AskGodFromListGUIClientState(Client client) {
        super(client);
        gui = (GUI)client.getUI();
        mainScene = JavaFXApp.getPrimaryScene();
    }

    public void returnToMenu(){
        client.moveToState(ClientState.WELCOME_SCREEN);
    }

    public void setChosenGod(ReducedGod god){
        this.chosenGod = god;
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
        if(client.isCurrentlyActive()){
            System.out.println("Active!");
            mainScene.getStylesheets().add(getClass().getResource("/css/god-selection.css").toExternalForm());
            SceneLoader.loadFromFXML("/fxml/AskGodFromList.fxml", mainScene, client, this, ClientState.ASK_GOD_FROM_LIST, true, true);
        } else {
            System.out.println("Passive!");
            SceneLoader.loadFromFXML("/fxml/AskGodFromListPassive.fxml", mainScene, client, this, ClientState.ASK_GOD_FROM_LIST, true, true);
        }

    }
}
