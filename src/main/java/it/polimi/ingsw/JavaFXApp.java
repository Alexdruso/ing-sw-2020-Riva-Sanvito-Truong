package it.polimi.ingsw;

import it.polimi.ingsw.client.ui.gui.GUI;
import it.polimi.ingsw.client.ui.gui.utils.CSSFile;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class JavaFXApp extends Application {

    private static Stage primaryStage;
    private static Scene primaryScene;
    private static GUI gui;

    private static final Object sceneLock = new Object();
    private static boolean initialized = false;
    private static final Logger LOGGER = Logger.getLogger(JavaFXApp.class.getName());

    @Override
    public void start(Stage stage) throws Exception {
        synchronized (sceneLock) {
            primaryStage = stage;

            stage.setTitle("Santorini - GC02");

            Pane root = new StackPane();
            primaryScene = new Scene(root, 1280, 720);

            primaryScene.getStylesheets().addAll(
                    Arrays.stream(CSSFile.values()).map(x -> x.CSSForm).collect(Collectors.toList())
            );
            primaryScene.setFill(Color.BLACK); //Ready to fade-in

            stage.setScene(primaryScene);
            stage.setFullScreen(false); //TODO: get this from args maybe?
            initialized = true;
            sceneLock.notifyAll();
        }

        stage.show();
    }

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

    public static void setNextRoot(Parent root){
        //primaryStage = stage;
        return;
    }

    public static void launchGUI(GUI gui) {
        JavaFXApp.gui = gui;
        Application.launch();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        TestGUIApp.stop();
    }
}
