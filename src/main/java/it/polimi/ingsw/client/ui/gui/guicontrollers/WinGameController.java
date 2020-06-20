package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.WinGameGUIClientState;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WinGameController extends AbstractController {
    private static final Logger LOGGER = Logger.getLogger(WinGameController.class.getName());

    @FXML Label mainPrompt;
    @FXML Label winnerName;

    @FXML public void handleReconnect(ActionEvent event){
        ((WinGameGUIClientState)state).reconnect();
    }
    @FXML public void handleMenu(ActionEvent event){
        ((WinGameGUIClientState)state).returnToMenu();
    }

    public void setWinnerPrompts(){
        mainPrompt.setText(I18n.string(I18nKey.VICTORIOUS));
        winnerName.setText(I18n.string(I18nKey.CONGRATULATIONS_YOU_WON));
    }

    public void setLoserPrompts(){
        mainPrompt.setText(I18n.string(I18nKey.LOSER));
        winnerName.setText(String.format(I18n.string(I18nKey.S_WON), client.getCurrentActiveUser().getNickname()));
    }

    @Override
    public void handleError(String message) {
        LOGGER.log(Level.SEVERE, message);
    }
}
