package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.ConnectToServerGUIClientState;
import it.polimi.ingsw.client.ui.gui.GUIClientState;
import it.polimi.ingsw.client.ui.gui.SetNicknameGUIClientState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SetNicknameController extends AbstractController{

    @FXML
    TextField nicknameField;

    @FXML
    Button setNicknameButton;

    @FXML
    Button menuButton;

    @FXML
    public void handleMenuButton(ActionEvent event){
        ((SetNicknameGUIClientState)state).returnToMenu();
    }

    @FXML
    public void handleNicknameButton(ActionEvent event){
        ((SetNicknameGUIClientState)state).setNickname(nicknameField.getText());
    }

    @Override
    public void handleError(String message) {

    }
}
