package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.LoseGameGUIClientState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class LoseGameController extends AbstractController {

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

    }
}
