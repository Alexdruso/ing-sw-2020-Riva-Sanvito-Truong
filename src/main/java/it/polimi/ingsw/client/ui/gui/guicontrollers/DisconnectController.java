package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.DisconnectGUIClientState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the Disconnect state
 */
public class DisconnectController extends AbstractController {
    private static final Logger LOGGER = Logger.getLogger(DisconnectController.class.getName());


    /**
     * Handles the reconnection button
     * @param event the mouse click event
     */
    @FXML
    private void handleReconnect(ActionEvent event){
        ((DisconnectGUIClientState)state).reconnect();
    }

    /**
     * Handles menu button on screen
     * @param event the mouse click event
     */
    @FXML
    private void handleMenu(ActionEvent event){
            ((DisconnectGUIClientState)state).returnToMenu();
    }

    @Override
    public void handleError(String message) {
        LOGGER.log(Level.SEVERE, message);

    }
}
