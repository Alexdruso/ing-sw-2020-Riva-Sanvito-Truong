package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.LoseGameGUIClientState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoseGameController extends AbstractController {
    private static final Logger LOGGER = Logger.getLogger(LoseGameController.class.getName());

    @FXML
    private void handleSpectate(ActionEvent event){
        ((LoseGameGUIClientState)state).spectate();
    }

    @FXML
    private void handleReconnect(ActionEvent event){
        ((LoseGameGUIClientState)state).reconnect();
    }

    @FXML
    private void handleMenu(ActionEvent event){
            ((LoseGameGUIClientState)state).returnToMenu();
    }

    @Override
    public void handleError(String message) {
        LOGGER.log(Level.SEVERE, message);

    }
}
