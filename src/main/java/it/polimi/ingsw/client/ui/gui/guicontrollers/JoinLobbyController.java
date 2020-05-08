package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.JoinLobbyGUIClientState;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class JoinLobbyController extends AbstractController {

    @FXML
    Label welcomeLabel;

    @FXML
    public void handleMenuButton(ActionEvent event){
        ((JoinLobbyGUIClientState)state).returnToMenu();
    }

    @Override
    public void handleError(String message) {

    }

    @Override
    public void setupController() {
        welcomeLabel.setText(String.format(I18n.string(I18nKey.WELCOME_TO_THE_SERVER_S), client.getNickname()));
    }

    public void stopAnimation(){

    }
}
