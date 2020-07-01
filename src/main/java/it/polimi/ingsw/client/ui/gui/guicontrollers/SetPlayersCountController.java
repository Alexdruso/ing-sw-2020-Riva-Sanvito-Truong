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


    /**
     * This method sends to the TurnState the information that the player wants to play in a two-player game
     * @param event the mouse click event
     */
    @FXML
    void twoPlayersSelected(ActionEvent event){
        ((SetPlayersCountGUIClientState)state).setPlayersCount(2);

    }

    /**
     * This method sends to the TurnState the information that the player wants to play in a three-player game
     * @param event the mouse click event
     */
    @FXML
    void threePlayersSelected(ActionEvent event){
        ((SetPlayersCountGUIClientState)state).setPlayersCount(3);
    }

    /**
     * Handles menu button on screen
     * @param event the mouse click event
     */
    @FXML
    void handleMenuButton(ActionEvent event){
        ((SetPlayersCountGUIClientState)state).returnToMenu();
    }

    /**
     * JavaFX initialization method
     */
    @FXML
    void initialize(){
        errorLabel.setOpacity(0);
    }

    @Override
    public void handleError(String message) {
        errorLabel.setOpacity(1);
    }
}
