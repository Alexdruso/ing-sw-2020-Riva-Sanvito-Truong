package it.polimi.ingsw;

import it.polimi.ingsw.client.ui.gui.GUI;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;


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

            Parent root = new StackPane();
            primaryScene = new Scene(root, 1280, 720);

            primaryScene.getStylesheets().add(getClass().getResource("/css/common.css").toExternalForm());

            stage.setScene(primaryScene);
            stage.setFullScreen(true);
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
