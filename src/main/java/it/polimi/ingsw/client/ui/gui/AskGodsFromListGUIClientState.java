package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractAskGodsFromListClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.guicontrollers.AskGodsFromListPassiveController;
import it.polimi.ingsw.client.ui.gui.utils.SavedScene;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderFactory;
import it.polimi.ingsw.utils.networking.transmittables.ReducedGod;

public class AskGodsFromListGUIClientState extends AbstractAskGodsFromListClientState implements GUIClientState{
    private SavedScene savedScene;
    private int selectedCount = 0;
    private int playersCount;
    private boolean wasPassive = false;
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
        client.closeConnection();
    }

    public void addChosenGod(ReducedGod god){
        chosenGods.add(god);
        selectedCount++;
        if(selectedCount == playersCount){
            notifyUiInteraction();
        }
    }

    /**
     * Triggers the operations to perform when exiting the current state
     */
    @Override
    public void tearDown() {
        if(wasPassive){
            ((AskGodsFromListPassiveController)savedScene.controller).stopAnimation();
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
            wasPassive = false;
        } else {
            sceneLoaderFactory = new SceneLoaderFactory("/fxml/AskGodsFromListPassive.fxml", client);
            wasPassive = true;
        }
        sceneLoaderFactory
                .setState(ClientState.ASK_GODS_FROM_LIST, this)
                .setFadeInDuration(2000)
                .build()
                .executeSceneChange();
        savedScene = ((GUI)client.getUI()).getCurrentScene();
    }
}
