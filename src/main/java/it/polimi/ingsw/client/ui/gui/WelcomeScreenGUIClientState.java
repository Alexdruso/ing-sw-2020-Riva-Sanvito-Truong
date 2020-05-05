package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.JavaFXApp;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractWelcomeScreenState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class WelcomeScreenGUIClientState extends AbstractWelcomeScreenState implements GUIClientState {
    private final GUI gui;
    private final Scene mainScene;
    private final Stage primaryStage;
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());

    /**
     * Instantiates a new ClientState.
     *
     * @param client the client
     */
    public WelcomeScreenGUIClientState(Client client) {
        super(client);
        gui = (GUI)client.getUI();
        primaryStage = JavaFXApp.getPrimaryStage();
        mainScene = JavaFXApp.getPrimaryScene();
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
        mainScene.getStylesheets().add(getClass().getResource("/css/MainMenu.css").toExternalForm());
        SceneLoader.loadFromFXML("/fxml/MainMenu.fxml", mainScene, client, this, ClientState.WELCOME_SCREEN, false);
        /*
        try {
            Optional<SavedScene> savedRoot = gui.getScene(ClientState.WELCOME_SCREEN);
            Parent root;
            WelcomeScreenController controller;
            SavedScene savedScene;

            if(savedRoot.isEmpty()){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
                root = loader.load();
                controller = loader.getController();
                controller.setClient(client);
                savedScene = new SavedScene(controller, root);
                gui.addScene(ClientState.WELCOME_SCREEN, savedScene);
                root.setCache(true);
                root.setCacheHint(CacheHint.SPEED);
            } else {
                savedScene = savedRoot.get();
                root = savedScene.root;
                controller = (WelcomeScreenController)savedRoot.get().controller;
            }

            gui.setCurrentScene(savedScene);
            controller.setState(this); //Always needs to be updated, since states are created on demand

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    root.setOpacity(0);
                    mainScene.setRoot(root);
                    FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), mainScene.getRoot());
                    fadeIn.setInterpolator(Interpolator.EASE_OUT);
                    fadeIn.setFromValue(0.0);
                    fadeIn.setToValue(1.0);
                    fadeIn.play();
                }
            });

        }catch (IOException e){
            //Could not load, probably fxml file or css file don't exist
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

         */
    }
}
