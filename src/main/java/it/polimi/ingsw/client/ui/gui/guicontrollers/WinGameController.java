package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.WinGameGUIClientState;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the WinGame state
 */
public class WinGameController extends AbstractController {
    private static final Logger LOGGER = Logger.getLogger(WinGameController.class.getName());

    @FXML Label mainPrompt;
    @FXML Label winnerName;


    /**
     * This method sends to the ClientState the information that the player wants to join another game
     * @param event the mouse click event
     */
    @FXML void handleReconnect(ActionEvent event){
        ((WinGameGUIClientState)state).reconnect();
    }

    /**
     * This method sends to the ClientState the information that the player wants to go back to the menu
     * @param event the mouse click event
     */
    @FXML void handleMenu(ActionEvent event){
        ((WinGameGUIClientState)state).returnToMenu();
    }

    /**
     * This method sets the prompt and the label for the winning player
     */
    public void setWinnerPrompts(){
        mainPrompt.setText(I18n.string(I18nKey.VICTORIOUS));
        winnerName.setText(I18n.string(I18nKey.CONGRATULATIONS_YOU_WON));
    }

    /**
     * This method sets the prompt and the label for the losing players
     */
    public void setLoserPrompts(){
        mainPrompt.setText(I18n.string(I18nKey.LOSER));
        winnerName.setText(String.format(I18n.string(I18nKey.S_WON), client.getCurrentActiveUser().getNickname()));
    }

    @Override
    public void handleError(String message) {
        LOGGER.log(Level.SEVERE, message);
    }
}
