package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractJoinLobbyClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.guicontrollers.JoinLobbyController;
import it.polimi.ingsw.client.ui.gui.utils.SavedScene;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

public class JoinLobbyGUIClientState extends AbstractJoinLobbyClientState implements GUIClientState{
    private SavedScene savedScene;
    private static final Logger LOGGER = Logger.getLogger(WaitPlayersGUIClientState.class.getName());

    /**
     * Instantiates a new JOIN_LOBBY ClientState.
     *
     * @param client the client
     */
    public JoinLobbyGUIClientState(Client client) {
        super(client);
    }

    public void returnToMenu(){
        client.moveToState(ClientState.WELCOME_SCREEN);
        client.closeConnection();
    }

    /**
     * Triggers the operations to perform when exiting the current state
     */
    @Override
    public synchronized void tearDown() {
        while(savedScene == null) {
            try{
                wait();
            } catch (InterruptedException e){
                LOGGER.log(Level.FINE, "Interrupting thread following InterruptedException", e);
                Thread.currentThread().interrupt();
            }
        }
        ((JoinLobbyController)savedScene.controller).stopAnimation();
    }

    /**
     * Function called by the main thread that renders the current state to the UI.
     * This function is the only one of this class allowed to be synchronous with the user input.
     * Please, be aware that calls to this function must be either:
     * - guaranteed not to happen concurrently with a Client state change
     * - or the implementation of this function must be self-sufficient (i.e., it does not depend on calls of render of previous states)
     */
    @Override
    public synchronized void render() {
        //FIXME: this synchronization will be replaced with a render queue in the Client
        SceneLoaderFactory sceneLoaderFactory = new SceneLoaderFactory("/fxml/JoinLobby.fxml", client);
        sceneLoaderFactory.setState(ClientState.JOIN_LOBBY, this).build().executeSceneChange();
        savedScene = ((GUI)client.getUI()).getCurrentScene();
        notifyAll();
    }
}
