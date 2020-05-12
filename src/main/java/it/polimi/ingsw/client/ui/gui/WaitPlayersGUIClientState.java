package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.JavaFXApp;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractWaitPlayersClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.guicontrollers.WaitPlayersController;
import it.polimi.ingsw.client.ui.gui.utils.SavedScene;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WaitPlayersGUIClientState extends AbstractWaitPlayersClientState implements GUIClientState {
    private final GUI gui;
    private final Stage primaryStage;
    private final Scene mainScene;
    private SavedScene savedScene;
    private static final Logger LOGGER = Logger.getLogger(WaitPlayersGUIClientState.class.getName());

    /**
     * Instantiates a new WAIT_PLAYERS ClientState.
     *
     * @param client the client
     */
    public WaitPlayersGUIClientState(Client client) {
        super(client);
        gui = (GUI)client.getUI();
        primaryStage = JavaFXApp.getPrimaryStage();
        mainScene = primaryStage.getScene();
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
        ((WaitPlayersController)savedScene.controller).stopAnimation();
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
        savedScene = SceneLoader.loadFromFXML("/fxml/WaitPlayers.fxml", mainScene, client, this, ClientState.WAIT_PLAYERS, true);
        notifyAll();
    }
}
