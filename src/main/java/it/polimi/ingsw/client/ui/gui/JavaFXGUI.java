package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.ui.gui.utils.CSSFile;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * This is the main JavaFX Application class. Its start method will be invoked by the SantoriniApp
 */
public class JavaFXGUI extends Application {
    private static final Logger LOGGER = Logger.getLogger(JavaFXGUI.class.getName());
    private static Stage primaryStage;
    private static Scene primaryScene;

    private static final Pane mainRoot = new StackPane();
    private static final Pane overlayRoot = new StackPane();

    private static final Object sceneLock = new Object();
    private static boolean initialized = false;
    static Runnable onExit;


    @Override
    public void start(Stage stage) throws Exception {
        synchronized (sceneLock) {
            primaryStage = stage;

            stage.setTitle("Santorini - GC02");

            overlayRoot.setMouseTransparent(true);
            ((StackPane)overlayRoot).setAlignment(Pos.CENTER);
            mainRoot.setId("root");

            mainRoot.setCache(true);
            mainRoot.setCacheHint(CacheHint.SPEED);
            overlayRoot.setCache(true);
            overlayRoot.setCacheHint(CacheHint.SPEED);

            Pane root = new StackPane(mainRoot, overlayRoot);

            primaryScene = new Scene(root, 1280, 720);

            primaryScene.getStylesheets().addAll(
                    Arrays.stream(CSSFile.values()).map(x -> x.cssForm).collect(Collectors.toList())
            );
            primaryScene.setFill(Color.BLACK); //Makes the fade go to black instead of white

            stage.setScene(primaryScene);
            stage.setFullScreen(false);
            initialized = true;
            sceneLock.notifyAll();
        }

        stage.show();
    }

    /**
     * This method sets the main root of the scene
     * @param newRoot
     */
    public static void setMainRoot(Pane newRoot){
        mainRoot.getChildren().clear();
        mainRoot.getChildren().add(newRoot);
    }

    /**
     * This method retrieves the main root of the scene
     * @return
     */
    public static Pane getMainRoot() {
        return mainRoot;
    }

    /**
     * This method sets the root for the overlay
     * @param newRoot
     */
    public static void setOverlayRoot(Pane newRoot){
        overlayRoot.getChildren().clear();
        overlayRoot.getChildren().add(newRoot);
    }

    /**
     * This method retrieves the overlay root of the scene
     * @return
     */
    public static Pane getOverlayRoot() {
        return overlayRoot;
    }

    /**
     * This method retrieves the Scene instance
     * @return
     */
    public static Scene getPrimaryScene(){
        synchronized(sceneLock){
            while (!initialized) {
                try {
                    sceneLock.wait();
                } catch (InterruptedException e){
                    LOGGER.log(Level.FINE, "Interrupting thread following InterruptedException", e);
                    Thread.currentThread().interrupt();
                }
            }
            return primaryScene;
        }
    }

    /**
     * This method retrieves the Stage instance
     * @return
     */
    public static Stage getPrimaryStage(){
        synchronized(sceneLock){
            while (!initialized) {
                try {
                    sceneLock.wait();
                } catch (InterruptedException e){
                    LOGGER.log(Level.FINE, "Interrupting thread following InterruptedException", e);
                    Thread.currentThread().interrupt();
                }
            }
            return primaryStage;
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        onExit.run();
    }

    public static void launchJavaFX() {
        Application.launch();
    }
}
