package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.JavaFXApp;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractConnectToServerClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

    public void returnToMenu(){
        client.moveToState(ClientState.WELCOME_SCREEN);
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
        SceneLoader.loadFromFXML("/fxml/ConnectToServer.fxml", mainScene, client, this, ClientState.CONNECT_TO_SERVER, true);
        /*
        try {
            Optional<SavedScene> savedRoot = gui.getScene(ClientState.CONNECT_TO_SERVER);
            Parent root;
            ConnectToServerController controller;
            SavedScene savedScene;
            if(savedRoot.isEmpty()){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ConnectToServer.fxml"));
                root = loader.load();
                controller = loader.getController();
                controller.setClient(client);
                savedScene = new SavedScene(controller, (Pane)root);
                gui.addScene(ClientState.CONNECT_TO_SERVER, savedScene);
                root.setCache(true);
                root.setCacheHint(CacheHint.SPEED);
            } else {
                savedScene = savedRoot.get();
                root = savedScene.root;
                controller = (ConnectToServerController) savedScene.controller;
            }

            controller.setState(this); //Always needs to be updated, since states are created on demand
            gui.setCurrentScene(savedScene);

            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), mainScene.getRoot());
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.play();

            fadeOut.setOnFinished((event) ->
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        //mainScene.getStylesheets().add(getClass().getResource("/css/MainMenu.css").toExternalForm());
                        root.setOpacity(0);
                        mainScene.setRoot(root);
                        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), root);
                        fadeIn.setInterpolator(Interpolator.EASE_OUT);
                        fadeIn.setFromValue(0.0);
                        fadeIn.setToValue(1.0);
                        fadeIn.play();
                    }
                })
            );

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
         */
    }
}
