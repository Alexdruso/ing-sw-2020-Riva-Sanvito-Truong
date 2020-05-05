package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.SetNicknameGUIClientState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SetNicknameController extends AbstractController{

    @FXML
    TextField nicknameField;

    @FXML
    Button setNicknameButton;

    @FXML
    Button menuButton;

    @FXML
    Label errorLabel;

    @FXML
    public void handleMenuButton(ActionEvent event){
        ((SetNicknameGUIClientState)state).returnToMenu();
    }

    @FXML
    public void handleNicknameButton(ActionEvent event){
        if(nicknameField.getLength() != 0){
            ((SetNicknameGUIClientState)state).setNickname(nicknameField.getText());
        } else {
            errorLabel.setText("Invalid nickname!");
            errorLabel.setOpacity(1);
        }
    }

    @FXML
    public void initialize(){
        errorLabel.setOpacity(0);
    }

    @Override
    public void handleError(String message) {
        //Nickname was already taken
        errorLabel.setText("Nickname is already taken.");
        errorLabel.setOpacity(1);
    }
}
