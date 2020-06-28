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

/**
 * This class is a helper to load the scene while applying various customization effects (such as animations)
 * and executes the steps that initialize the scene and its controller.
 */
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

    /**
     * Class constructor
     * @param loader the SceneLoaderFactory from which to build the SceneLoader
     */
    protected SceneLoader(SceneLoaderBuilder loader){
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

    /**
     * The method which starts the scene loading process
     */
    public void executeSceneChange(){
        SavedScene scene = null;
        GUI gui = (GUI)client.getUI();
        if(gui.getCurrentScene() == null || forceSceneChange || !fxmlFile.equals(gui.getCurrentScene().fxmlFile)){
            if(gui.getCurrentScene() != null){
                gui.getCurrentScene().controller.tearDown();
            }
            if(attemptLoadFromSaved){
                scene = loadFromSaved(fxmlFile, (GUI)client.getUI());
            }
            if (scene == null){
                scene = loadAndSave(gui);
                if (scene == null) return;
            }
            scene.controller.setClient(client);
            scene.controller.setupController();
            scene.controller.setState(state);
            applySceneFade(scene);
            if(replaceOldScene){
                gui.setCurrentScene(scene);
            }
        } else {
            scene = ((GUI) client.getUI()).getCurrentScene();
            scene.controller.setClient(client);
            scene.controller.setupController();
            scene.controller.setState(state);
        }
        SavedScene finalScene = scene;
        Platform.runLater(finalScene.controller::onSceneShow);
    }

    /**
     * This method applies fade in and fade out effects to the current scene and loads the new scene
     * @param savedScene the new scene to load in
     */
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

    /**
     * This method attempts to load an .fxml file and, if successful saves the corresponding
     * SavedScene for later use
     *
     * @param gui the GUI instance
     * @return the SavedScene if the loading was successful, null otherwise
     */
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

    /**
     * This method loads the FXML from memory, creating a SavedScene containing all the scene elements
     * @param file a String representing the path in the resource folder pointing to the .fxml file
     * @param clientState the State bound to the scene
     * @param resourceBundle a ResourceBundle to load i18n strings from
     * @return a SavedScene instance containing all the scene components
     * @throws IOException if the path was invalid
     */
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

    /**
     * This method retrieves a previously stored SavedScene
     * @param file the .fxml file whose scene we want to load
     * @param gui the GUI instance
     * @return the SavedScene instance
     */
    public static SavedScene loadFromSaved(String file, GUI gui){
        return gui.getScene(file);
    }

    /**
     * This method retrieves the ResourceBundle for the currently set locale
     * @return the ResourceBundle
     */
    private static ResourceBundle geti18n(){
        return I18n.getResourceBundle();
    }

    /**
     * This method executes the real scene loading by setting the root and starts the fade in animation
     * @param mainScene the Scene object in which the scene is to be loaded
     * @param newRoot  the Root object to be loaded in the scene
     * @param duration the fade in duration
     */
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

    /**
     * This method executes the fade out and optionally executes the fade in
     * @param mainScene the Scene object in which the animation is to be executed
     * @param newRoot  the root to be loaded in the scene after the fade out
     * @param fadeOutDuration the fade out duration
     * @param fadeInDuration the fade in duration
     */
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

    /**
     * This method executes the scene loading (as an overlay) by setting the overlay root and starts the blur in animation
     * @param newOverlay the root to be loaded in the scene
     * @param duration the blur in duration
     */
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
                newOverlay.setTranslateY(JavaFXGUI.getPrimaryStage().getHeight()); //Put this back on the screen
                TranslateTransition tt = new TranslateTransition(Duration.millis(500), newOverlay);
                tt.setToY(0);
                JavaFXGUI.setOverlayRoot((Pane)newOverlay);
                JavaFXGUI.getOverlayRoot().setMouseTransparent(false);
                tt.play();
            });
            timeline.play();
        });
    }

    /**
     * This method executes the blur out
     * @param duration the blur out duration
     */
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
}
