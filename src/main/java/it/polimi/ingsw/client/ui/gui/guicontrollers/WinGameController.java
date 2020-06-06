package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.WinGameGUIClientState;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WinGameController extends AbstractController {

    @FXML
    Label mainPrompt;

    @FXML
    Label winnerName;

    @FXML
    public void handleJoinLobby(ActionEvent event){
        ((WinGameGUIClientState)state).joinLobby();
    }
    @FXML
    public void handleMenu(ActionEvent event){
        ((WinGameGUIClientState)state).returnToMenu();
    }

    public void setWinnerPrompts(){
        mainPrompt.setText(I18n.string(I18nKey.VICTORIOUS));
        winnerName.setText(I18n.string(I18nKey.CONGRATULATIONS_YOU_WON));
    }

    public void setLoserPrompts(){
        mainPrompt.setText(I18n.string(I18nKey.LOSER));
        winnerName.setText(String.format(I18n.string(I18nKey.S_WON), client.getCurrentActiveUser().nickname));
    }

    @Override
    public void handleError(String message) {

    }
}
