package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.SetPlayersCountGUIClientState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controller for the SetPlayersCount state
 */
public class SetPlayersCountController extends AbstractController{

    @FXML
    Label errorLabel;

    @FXML
    public void twoPlayersSelected(ActionEvent event){
        ((SetPlayersCountGUIClientState)state).setPlayersCount(2);

    }

    @FXML
    public void threePlayersSelected(ActionEvent event){
        ((SetPlayersCountGUIClientState)state).setPlayersCount(3);
    }

    @FXML
    public void handleMenuButton(ActionEvent event){
        ((SetPlayersCountGUIClientState)state).returnToMenu();
    }

    @FXML
    public void initialize(){
        errorLabel.setOpacity(0);
    }

    @Override
    public void handleError(String message) {
        errorLabel.setOpacity(1);
    }
}
