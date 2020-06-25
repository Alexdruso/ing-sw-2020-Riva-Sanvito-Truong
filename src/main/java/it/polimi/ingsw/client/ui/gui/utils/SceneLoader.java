package it.polimi.ingsw.client.ui.gui.utils;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.GUI;
import it.polimi.ingsw.client.ui.gui.JavaFXGUI;
import it.polimi.ingsw.client.ui.gui.guicontrollers.AbstractController;
import it.polimi.ingsw.utils.i18n.I18n;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.CacheHint;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SceneLoader {
    private static final Logger LOGGER = Logger.getLogger(SceneLoader.class.getName());

    private final String fxmlFile;
    private final Client client;
    private final ClientState clientState;
    private final Scene mainScene;
    private boolean doApplyFadeOut;
    private final boolean doApplyFirstFadeOut;
    private final boolean doApplyFadeIn;
    private final boolean attemptLoadFromSaved;
    private final boolean forceSceneChange;
    private final boolean replaceOldScene;
    private final double fadeInDuration;
    private final double fadeOutDuration;
    private final double blurInDuration;
    private final AbstractClientState state;

    protected SceneLoader(SceneLoaderFactory loader){
        this.fxmlFile = loader.fxmlFile;
        this.client = loader.client;
        this.clientState = loader.clientState;
        this.mainScene = loader.mainScene;
        this.doApplyFadeOut = loader.doApplyFadeOut;
        this.doApplyFirstFadeOut = loader.doApplyFirstFadeOut;
        this.doApplyFadeIn = loader.doApplyFadeIn;
        this.attemptLoadFromSaved = loader.attemptLoadFromSaved;
        this.forceSceneChange = loader.forceSceneChange;
        this.replaceOldScene = loader.replaceOldScene;
        this.fadeInDuration = loader.fadeInDuration;
        this.fadeOutDuration = loader.fadeOutDuration;
        this.blurInDuration = loader.blurInDuration;
        this.state = loader.state;
    }

    public void executeSceneChange(){
        SavedScene scene = null;
        GUI gui = (GUI)client.getUI();
        if(gui.getCurrentScene() == null || forceSceneChange || !fxmlFile.equals(gui.getCurrentScene().fxmlFile)){
            if(attemptLoadFromSaved){
                scene = loadFromSaved(fxmlFile, (GUI)client.getUI());
            }
            if (scene == null){
                scene = loadAndSave(gui);
                if (scene == null) return;
            }
            scene.controller.setClient(client);
            scene.controller.setupController();
            SavedScene finalScene = scene;
            Platform.runLater(finalScene.controller::onSceneShow);
            scene.controller.setState(state);
            applySceneFade(scene);
            gui.setCurrentScene(scene);
        } else {
            scene = ((GUI) client.getUI()).getCurrentScene();
            scene.controller.setClient(client);
            scene.controller.setupController();
            scene.controller.setState(state);
        }
        scene.controller.onSceneShow();
    }

    private void applySceneFade(SavedScene savedScene) {
        if(replaceOldScene){
            if (doApplyFadeOut){
                applyFadeOut(mainScene, savedScene.root, fadeOutDuration, fadeInDuration);
            }
            else {
                if (doApplyFadeIn) {
                    applyFadeIn(mainScene, savedScene.root, fadeInDuration);
                } else {
                    Platform.runLater(() -> JavaFXGUI.setMainRoot((Pane)savedScene.root));
                }
            }
        } else {
            applyBlurIn(savedScene.root, blurInDuration);
        }
    }

    private SavedScene loadAndSave(GUI gui) {
        SavedScene savedScene;
        try{
            savedScene = loadNewFXML(fxmlFile, clientState, geti18n());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
        gui.addScene(fxmlFile, savedScene);

        doApplyFadeOut = doApplyFirstFadeOut;
        return savedScene;
    }


    public static SavedScene loadNewFXML(String file, ClientState clientState, ResourceBundle resourceBundle) throws IOException {
        URL fileURL = SceneLoader.class.getResource(file);
        if(fileURL == null){
            throw new FileNotFoundException("No such FXML file: " + file);
        }
        FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource(file), resourceBundle);
        Parent root;
        root = loader.load();
        root.setCache(true);
        root.setCacheHint(CacheHint.SPEED);
        AbstractController controller = loader.getController();
        return new SavedScene(file, controller, root, clientState);
    }

    public static SavedScene loadFromSaved(String file, GUI gui){
        return gui.getScene(file);
    }

    private static ResourceBundle geti18n(){
        return I18n.getResourceBundle();
    }

    public static void applyFadeIn(Scene mainScene, Parent newRoot, double duration){
        Platform.runLater(() -> {
            mainScene.getRoot().setOpacity(0);
            JavaFXGUI.setMainRoot((Pane)newRoot);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(duration), mainScene.getRoot());
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
            fadeOut.setOnFinished(event -> applyFadeIn(mainScene, newRoot, fadeInDuration));
        });
    }

    public static void applyBlurIn(Parent newOverlay, double duration){
        Platform.runLater(() -> {
            BoxBlur blur = new BoxBlur();
            Timeline timeline = new Timeline(
                    new KeyFrame(
                            Duration.millis(duration),
                            new KeyValue(blur.heightProperty(), 15, Interpolator.EASE_BOTH),
                            new KeyValue(blur.widthProperty(), 15, Interpolator.EASE_BOTH)
                    ));
            JavaFXGUI.getMainRoot().setEffect(blur);
            timeline.setOnFinished(event -> {
                newOverlay.setTranslateY(0); //Put this back on the screen
                JavaFXGUI.setOverlayRoot((Pane)newOverlay);
                JavaFXGUI.getOverlayRoot().setMouseTransparent(false);
            });
            timeline.play();
        });
    }

    public static void applyBlurOut(double duration){
        Platform.runLater(() -> {
            BoxBlur blur = (BoxBlur)JavaFXGUI.getMainRoot().getEffect();
            Timeline timeline = new Timeline(
                    new KeyFrame(
                            Duration.millis(duration/2) ,
                            new KeyValue(
                                    JavaFXGUI.getOverlayRoot().getChildren().get(0).translateYProperty(),
                                    JavaFXGUI.getPrimaryScene().getHeight()*2,
                                    Interpolator.EASE_BOTH
                            )
                    ),
                    new KeyFrame(
                            Duration.millis(duration),
                            new KeyValue(blur.heightProperty(), 0, Interpolator.EASE_BOTH),
                            new KeyValue(blur.widthProperty(), 0, Interpolator.EASE_BOTH)
                    )
            );
            timeline.setOnFinished(event -> {
                JavaFXGUI.getOverlayRoot().getChildren().clear();
                JavaFXGUI.getOverlayRoot().setMouseTransparent(true);
            });
            timeline.play();
        });
    }

    public static void clearOverlay(){
        Platform.runLater(() -> {
            JavaFXGUI.getMainRoot().setEffect(null);
            JavaFXGUI.getOverlayRoot().getChildren().clear();
        });
    }
}
