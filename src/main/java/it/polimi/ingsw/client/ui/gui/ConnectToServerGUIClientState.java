package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.JavaFXApp;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractClientState;
import it.polimi.ingsw.client.clientstates.AbstractConnectToServerClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.guicontrollers.ConnectToServerController;
import it.polimi.ingsw.client.ui.gui.guicontrollers.WelcomeScreenController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectToServerGUIClientState extends AbstractConnectToServerClientState implements GUIClientState{
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());
    private final GUI gui;
    private final Stage primaryStage;
    private final Scene mainScene;
    /**
     * Instantiates a new JOIN_LOBBY ClientState.
     *
     * @param client the client
     */
    public ConnectToServerGUIClientState(Client client) {
        super(client);
        gui = (GUI)client.getUI();
        primaryStage = JavaFXApp.getPrimaryStage();
        mainScene = primaryStage.getScene();
    }

    public void setHostPort(String host, String port){
        this.host = host;
        this.port = Integer.parseInt(port);
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
        try {
            Optional<SavedScene> savedRoot = gui.getScene(ClientState.CONNECT_TO_SERVER);
            Parent root;
            ConnectToServerController controller;
            if(savedRoot.isEmpty()){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ConnectToServer.fxml"));
                root = loader.load();
                controller = loader.getController();
                controller.setClient(client);
                gui.addScene(ClientState.CONNECT_TO_SERVER, (Pane)root, controller);
            } else {
                root = savedRoot.get().root;
                controller = (ConnectToServerController) savedRoot.get().controller;
            }

            controller.setState(this); //Always needs to be updated, since states are created on demand

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    mainScene.setRoot(root);
                    //mainScene.getStylesheets().add(getClass().getResource("/css/MainMenu.css").toExternalForm());
                }
            });
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
