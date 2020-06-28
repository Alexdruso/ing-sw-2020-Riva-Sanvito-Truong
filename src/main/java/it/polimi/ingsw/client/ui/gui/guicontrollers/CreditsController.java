package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the Credits
 */
public class CreditsController extends AbstractController{
    private static final Logger LOGGER = Logger.getLogger(CreditsController.class.getName());

    @FXML
    void handleMenuButton(ActionEvent event){
        SceneLoaderBuilder sceneLoaderBuilder = new SceneLoaderBuilder("/fxml/MainMenu.fxml", client);
        sceneLoaderBuilder.setAttemptLoadFromSaved(true).build().executeSceneChange();
    }

    @Override
    public void handleError(String message) {
        LOGGER.log(Level.SEVERE, message);

    }
}
