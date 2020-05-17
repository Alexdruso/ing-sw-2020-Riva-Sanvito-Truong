package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.JavaFXApp;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoader;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;


public class CreditsController extends AbstractController{
    Stage primaryStage;

    @FXML
    public void handleMenuButton(ActionEvent event){
        SceneLoaderFactory sceneLoaderFactory = new SceneLoaderFactory("/fxml/MainMenu.fxml", client);
        sceneLoaderFactory.setAttemptLoadFromSaved(true).build().executeSceneChange();
    }

    @FXML
    public void initialize(){
        primaryStage = JavaFXApp.getPrimaryStage();
    }


    @Override
    public void handleError(String message) {

    }
}
