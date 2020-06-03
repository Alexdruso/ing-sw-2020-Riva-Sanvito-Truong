package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractAskStartPlayerClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.guicontrollers.AskGodsFromListPassiveController;
import it.polimi.ingsw.client.ui.gui.guicontrollers.AskStartPlayerPassiveController;
import it.polimi.ingsw.client.ui.gui.utils.SavedScene;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderFactory;
import it.polimi.ingsw.utils.messages.ReducedUser;

public class AskStartPlayerGUIClientState extends AbstractAskStartPlayerClientState implements GUIClientState{
    private SavedScene savedScene;
    private boolean wasPassive = false;
    /**
     * Instantiates a new ASK_START_PLAYER ClientState.
     *
     * @param client the client
     */
    public AskStartPlayerGUIClientState(Client client) {
        super(client);
    }

    public void returnToMenu(){
        client.moveToState(ClientState.WELCOME_SCREEN);
        client.closeConnection();
    }

    public void selectUser(ReducedUser user){
        chosenUser = user;
        notifyUiInteraction();
    }

    /**
     * Triggers the operations to perform when exiting the current state
     */
    @Override
    public void tearDown() {
        if(wasPassive){
            ((AskStartPlayerPassiveController)savedScene.controller).stopAnimation();
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
            sceneLoaderFactory = new SceneLoaderFactory("/fxml/AskStartPlayer.fxml", client);
            wasPassive = false;
        } else {
            sceneLoaderFactory = new SceneLoaderFactory("/fxml/AskStartPlayerPassive.fxml", client);
            wasPassive = true;
        }
        sceneLoaderFactory.setState(ClientState.ASK_START_PLAYER, this).build().executeSceneChange();
        savedScene = ((GUI)client.getUI()).getCurrentScene();
    }
}
