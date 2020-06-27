package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractAskGodFromListClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.guicontrollers.AskGodFromListPassiveController;
import it.polimi.ingsw.client.ui.gui.utils.SavedScene;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderFactory;
import it.polimi.ingsw.utils.networking.transmittables.ReducedGod;

/**
 * A GUI-specific ASK_GOD_FROM_LIST client state.
 */
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

    /**
     * This method sends the client to the menu
     */
    public void returnToMenu(){
        client.moveToState(ClientState.WELCOME_SCREEN);
    }

    /**
     * This method is called to notify the Client that a God has been chosen
     * @param god the ReducedGod representing the chosen God
     */
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
                .forceSceneChange(true)
                .build()
                .executeSceneChange();
        savedScene = ((GUI)client.getUI()).getCurrentScene();
    }
}
