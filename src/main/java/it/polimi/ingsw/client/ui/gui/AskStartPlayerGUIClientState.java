package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractAskStartPlayerClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.guicontrollers.AskStartPlayerPassiveController;
import it.polimi.ingsw.client.ui.gui.utils.SavedScene;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderFactory;
import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;

/**
 * A GUI-specific ASK_START_PLAYER client state.
 */
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

    /**
     * This method sends the client to the menu
     */
    public void returnToMenu(){
        client.moveToState(ClientState.WELCOME_SCREEN);
    }

    /**
     * This method is used to select a ReducedUser to be the first player and to notify the Client about the selection
     * @param user the ReducedUser representing the player that will play first
     */
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
