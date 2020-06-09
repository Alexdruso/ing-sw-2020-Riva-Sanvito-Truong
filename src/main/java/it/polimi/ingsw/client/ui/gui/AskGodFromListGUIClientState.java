package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractAskGodFromListClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.guicontrollers.AskGodFromListPassiveController;
import it.polimi.ingsw.client.ui.gui.utils.CSSFile;
import it.polimi.ingsw.client.ui.gui.utils.SavedScene;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderFactory;
import it.polimi.ingsw.utils.networking.transmittables.ReducedGod;

public class AskGodFromListGUIClientState extends AbstractAskGodFromListClientState implements GUIClientState{
    private SavedScene savedScene;
    private boolean wasPassive = false;
    /**
     * Instantiates a new ClientState.
     *
     * @param client the client
     */
    public AskGodFromListGUIClientState(Client client) {
        super(client);
    }

    public void returnToMenu(){
        client.moveToState(ClientState.WELCOME_SCREEN);
        client.closeConnection();
    }

    public void setChosenGod(ReducedGod god){
        this.chosenGod = god;
        notifyUiInteraction();
    }

    /**
     * Triggers the operations to perform when exiting the current state
     */
    @Override
    public void tearDown() {
        if(wasPassive){
            ((AskGodFromListPassiveController)savedScene.controller).stopAnimation();
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
            sceneLoaderFactory = new SceneLoaderFactory("/fxml/AskGodFromList.fxml", client);
            wasPassive = false;
        } else {
            sceneLoaderFactory = new SceneLoaderFactory("/fxml/AskGodFromListPassive.fxml", client);
            wasPassive = true;
        }
        sceneLoaderFactory
                .setState(ClientState.ASK_GOD_FROM_LIST, this)
                .build()
                .executeSceneChange();
        savedScene = ((GUI)client.getUI()).getCurrentScene();
    }
}
