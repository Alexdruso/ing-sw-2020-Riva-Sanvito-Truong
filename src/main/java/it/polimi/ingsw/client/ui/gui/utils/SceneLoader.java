package it.polimi.ingsw.client.ui.gui.utils;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.GUI;
import it.polimi.ingsw.client.ui.gui.guicontrollers.AbstractController;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.CacheHint;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SceneLoader {
    private static final Logger LOGGER = Logger.getLogger(SceneLoader.class.getName());

    public static SavedScene loadFromFXML(String file, Scene mainScene, Client client,
                                          AbstractClientState state, ClientState clientState, boolean applyFadeOut){
        GUI gui = (GUI)client.getUI();

        Parent root;
        AbstractController controller;
        SavedScene savedScene;
        Boolean stateChanged = false;

        try {
            Optional<SavedScene> savedRoot = gui.getScene(clientState);

            if(savedRoot.isEmpty()){
                FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource(file));
                root = loader.load();
                controller = loader.getController();
                controller.setClient(client);
                savedScene = new SavedScene(controller, root, clientState);
                gui.addScene(clientState, savedScene);
                root.setCache(true);
                root.setCacheHint(CacheHint.SPEED);
            } else {
                savedScene = savedRoot.get();

                SavedScene currentScene = gui.getCurrentScene();
                if(!savedScene.equals(currentScene)){
                    stateChanged = true;
                }

                root = savedScene.root;
                controller = savedScene.controller;
            }

            controller.setState(state); //Always needs to be updated, since states are created on demand
            gui.setCurrentScene(savedScene);

            if(stateChanged){
                if(applyFadeOut){
                    applyFadeOut(mainScene, root);
                } else {
                    applyFadeIn(mainScene, root, 1500);
                }
            } else {
               mainScene.setRoot(root);
            }
            return savedScene;

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
    }

    public static void applyFadeIn(Scene mainScene, Parent newRoot, double duration){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                newRoot.setOpacity(0);
                mainScene.setRoot(newRoot);
                FadeTransition fadeIn = new FadeTransition(Duration.millis(duration), newRoot);
                fadeIn.setInterpolator(Interpolator.EASE_OUT);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            }
        });
    }

    public static void applyFadeOut(Scene mainScene, Parent newRoot){
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), mainScene.getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.play();
        fadeOut.setOnFinished((event) -> applyFadeIn(mainScene, newRoot, 500));
    }
}
