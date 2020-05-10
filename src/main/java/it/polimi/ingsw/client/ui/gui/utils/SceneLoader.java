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
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
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
        Boolean stateChanged = true;

        try {
            Optional<SavedScene> savedRoot = gui.getScene(clientState);

            if(savedRoot.isEmpty()){
                ResourceBundle resources = geti18n();
                FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource(file), resources);

                root = loader.load();

                controller = loader.getController();
                controller.setClient(client);

                controller.setupController();

                savedScene = new SavedScene(controller, root, clientState);
                gui.addScene(clientState, savedScene);

                root.setCache(true);
                root.setCacheHint(CacheHint.SPEED);
            } else {
                savedScene = savedRoot.get();

                SavedScene currentScene = gui.getCurrentScene();
                if(savedScene.equals(currentScene)){
                    stateChanged = false;
                }

                root = savedScene.root;
                controller = savedScene.controller;
            }

            controller.onSceneShow();

            controller.setState(state); //Always needs to be updated, since states are created on demand
            gui.setCurrentScene(savedScene);

            if(stateChanged){
                if(applyFadeOut){
                    applyFadeOut(mainScene, root);
                } else {
                    applyFadeIn(mainScene, root, 1500);
                }
            } else {
                Platform.runLater(() -> mainScene.setRoot(root));
            }
            return savedScene;

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
    }

    public static void loadNoCacheFromFXML(String file, Client client, Scene mainScene, boolean applyFadeOut){
        Parent root;
        try {
            ResourceBundle resources = geti18n();
            FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource(file), resources);
            root = loader.load();
            AbstractController controller = loader.getController();

            controller.setClient(client);
            controller.setupController();

            root.setCache(true);
            root.setCacheHint(CacheHint.SPEED);
            if(applyFadeOut){
                applyFadeOut(mainScene, root);
            } else {
                applyFadeIn(mainScene, root, 1500);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void loadSaved(ClientState clientState, Scene mainScene, Client client, boolean applyFadeOut){
        GUI gui = (GUI)client.getUI();
        SavedScene savedScene = gui.getScene(clientState).get();

        Pane root = (Pane)savedScene.root;
        AbstractController controller = savedScene.controller;

        gui.setCurrentScene(savedScene);

        applyFadeOut(mainScene, root);
    }

    public static ResourceBundle geti18n(){
        String LANGUAGE_ENV_VAR_NAME = "LANGUAGE";
        Locale locale;
        String language = System.getenv(LANGUAGE_ENV_VAR_NAME);
        try {
            locale = new Locale(language);
        }
        catch (NullPointerException ignored) {
            locale = Locale.getDefault();
        }
        return ResourceBundle.getBundle("i18n.strings", locale);
    }

    public static void applyFadeIn(Scene mainScene, Parent newRoot, double duration){
        Platform.runLater(() -> {
            newRoot.setOpacity(0);
            mainScene.setRoot(newRoot);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(duration), newRoot);
            fadeIn.setInterpolator(Interpolator.EASE_OUT);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
    }

    public static void applyFadeOut(Scene mainScene, Parent newRoot){
        Platform.runLater(() -> {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), mainScene.getRoot());
            fadeOut.setInterpolator(Interpolator.EASE_OUT);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.play();
            fadeOut.setOnFinished((event) -> applyFadeIn(mainScene, newRoot, 500));
        });
    }
}
