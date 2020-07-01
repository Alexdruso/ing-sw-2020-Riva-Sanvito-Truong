package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.LoseGameGUIClientState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the LoseGame state
 */
public class LoseGameController extends AbstractController {
    private static final Logger LOGGER = Logger.getLogger(LoseGameController.class.getName());

    /**
     * This method sends to the ClientState the information that the player wants to keep spectating the game
     * @param event the mouse click event
     */
    @FXML
    private void handleSpectate(ActionEvent event){
        ((LoseGameGUIClientState)state).spectate();
    }

    /**
     * This method sends to the ClientState the information that the player wants to connect to a new game
     * @param event the mouse click event
     */
    @FXML
    private void handleReconnect(ActionEvent event){
        ((LoseGameGUIClientState)state).reconnect();
    }

    /**
     * This method sends to the ClientState the information that the player wants to return to the menu
     * @param event the mouse click event
     */
    @FXML
    private void handleMenu(ActionEvent event){
            ((LoseGameGUIClientState)state).returnToMenu();
    }

    @Override
    public void handleError(String message) {
        LOGGER.log(Level.SEVERE, message);

    }
}
