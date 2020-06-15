package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.DisconnectGUIClientState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DisconnectController extends AbstractController {
    private static final Logger LOGGER = Logger.getLogger(DisconnectController.class.getName());

    @FXML
    private void handleReconnect(ActionEvent event){
        ((DisconnectGUIClientState)state).reconnect();
    }

    @FXML
    private void handleMenu(ActionEvent event){
            ((DisconnectGUIClientState)state).returnToMenu();
    }

    @Override
    public void handleError(String message) {
        LOGGER.log(Level.SEVERE, message);

    }
}
