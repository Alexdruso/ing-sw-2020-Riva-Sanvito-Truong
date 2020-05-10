package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.JavaFXApp;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;


public class CreditsController extends AbstractController{
    Stage primaryStage;

    @FXML
    public void handleMenuButton(ActionEvent event){
        primaryStage.getScene().getStylesheets().add(getClass().getResource("/css/main-menu.css").toExternalForm());
        SceneLoader.loadSaved(ClientState.WELCOME_SCREEN, primaryStage.getScene(), client, true);
    }

    @FXML
    public void initialize(){
        primaryStage = JavaFXApp.getPrimaryStage();
    }


    @Override
    public void handleError(String message) {

    }
}
