package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractAskGodsFromListClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.guicontrollers.AskGodsFromListPassiveController;
import it.polimi.ingsw.client.ui.gui.utils.SavedScene;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderBuilder;
import it.polimi.ingsw.utils.networking.transmittables.ReducedGod;

/**
 * A GUI-specific ASK_GODS_FROM_LIST client state.
 */
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

    /**
     * This method sends the client to the menu
     */
    public void returnToMenu(){
        client.moveToState(ClientState.WELCOME_SCREEN);
    }

    /**
     * This method is called to add a God to the selection list and, when enough Gods have been chose, send the notification
     * to the Client.
     *
     * @param god the ReducedGod instance of the chosen God
     */
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


    @Override
    public void render() {
        SceneLoaderBuilder sceneLoaderBuilder;
        if(client.isCurrentlyActive()){
            sceneLoaderBuilder = new SceneLoaderBuilder("/fxml/AskGodsFromList.fxml", client);
            wasPassive = false;
        } else {
            sceneLoaderBuilder = new SceneLoaderBuilder("/fxml/AskGodsFromListPassive.fxml", client);
            wasPassive = true;
        }
        sceneLoaderBuilder
                .setState(ClientState.ASK_GODS_FROM_LIST, this)
                .setFadeInDuration(2000)
                .build()
                .executeSceneChange();
        savedScene = ((GUI)client.getUI()).getCurrentScene();
    }
}
