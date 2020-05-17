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
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SceneLoader {
    private static final Logger LOGGER = Logger.getLogger(SceneLoader.class.getName());

    private String fxmlFile;
    private Client client;
    private ClientState clientState;
    private Scene mainScene;
    private boolean doApplyFadeOut;
    private boolean doApplyFirstFadeOut;
    private boolean doApplyFadeIn;
    private boolean attemptLoadFromSaved;
    private double fadeInDuration;
    private double fadeOutDuration;
    private String cssFile;
    private AbstractClientState state;

    protected SceneLoader(SceneLoaderFactory loader){
        this.fxmlFile = loader.fxmlFile;
        this.client = loader.client;
        this.clientState = loader.clientState;
        this.mainScene = loader.mainScene;
        this.doApplyFadeOut = loader.doApplyFadeOut;
        this.doApplyFirstFadeOut = loader.doApplyFirstFadeOut;
        this.doApplyFadeIn = loader.doApplyFadeIn;
        this.attemptLoadFromSaved = loader.attemptLoadFromSaved;
        this.fadeInDuration = loader.fadeInDuration;
        this.fadeOutDuration = loader.fadeOutDuration;
        this.cssFile = loader.cssFile;
        this.state = loader.state;
    }

    public void executeSceneChange(){
        SavedScene savedScene = null;
        GUI gui = (GUI)client.getUI();
        if(gui.getCurrentScene() == null || !fxmlFile.equals(gui.getCurrentScene().fxmlFile)){
            if(attemptLoadFromSaved){
                savedScene = loadFromSaved(fxmlFile, (GUI)client.getUI());
            }
            if(savedScene == null){
                savedScene = loadNewFXML(fxmlFile, clientState, geti18n());
                gui.addScene(fxmlFile, savedScene);
                doApplyFadeOut = doApplyFirstFadeOut;
            }
            savedScene.controller.setClient(client);
            savedScene.controller.setupController();
            savedScene.controller.onSceneShow();
            savedScene.controller.setState(state);
            if(cssFile != null){
                mainScene.getStylesheets().add(getClass().getResource(cssFile).toExternalForm());
            }
            if(doApplyFadeOut){
                applyFadeOut(mainScene, savedScene.root, fadeOutDuration, fadeInDuration);
            } else {
                if(doApplyFadeIn){
                    applyFadeIn(mainScene, savedScene.root, fadeInDuration);
                } else {
                    SavedScene finalSavedScene = savedScene;
                    Platform.runLater(() -> mainScene.setRoot(finalSavedScene.root));
                }
            }
            gui.setCurrentScene(savedScene);
        }
    }


    public static SavedScene loadNewFXML(String file, ClientState clientState, ResourceBundle resourceBundle){
        FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource(file), resourceBundle);
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e){
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
        root.setCache(true);
        root.setCacheHint(CacheHint.SPEED);
        AbstractController controller = loader.getController();
        return new SavedScene(file, controller, root, clientState);
    }

    public static SavedScene loadFromSaved(String file, GUI gui){
        SavedScene savedScene = gui.getScene(file);
        if(savedScene != null){
            return savedScene;
        } else {
            return null;
        }
    }

    private static ResourceBundle geti18n(){
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
            fadeIn.setInterpolator(Interpolator.EASE_BOTH);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
    }

    public static void applyFadeOut(Scene mainScene, Parent newRoot, double fadeOutDuration, double fadeInDuration){
        Platform.runLater(() -> {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(fadeOutDuration), mainScene.getRoot());
            fadeOut.setInterpolator(Interpolator.EASE_BOTH);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.play();
            fadeOut.setOnFinished((event) -> applyFadeIn(mainScene, newRoot, fadeInDuration));
        });
    }
}
