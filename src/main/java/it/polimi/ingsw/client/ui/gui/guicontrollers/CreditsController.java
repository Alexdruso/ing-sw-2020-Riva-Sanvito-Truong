package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.JavaFXGUI;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;


public class CreditsController extends AbstractController{
    Stage primaryStage;
    private static final Logger LOGGER = Logger.getLogger(CreditsController.class.getName());

    @FXML
    public void handleMenuButton(ActionEvent event){
        SceneLoaderFactory sceneLoaderFactory = new SceneLoaderFactory("/fxml/MainMenu.fxml", client);
        sceneLoaderFactory.setAttemptLoadFromSaved(true).build().executeSceneChange();
    }

    @FXML
    public void initialize(){
        primaryStage = JavaFXGUI.getPrimaryStage();
    }


    @Override
    public void handleError(String message) {
        LOGGER.log(Level.SEVERE, message);

    }
}
