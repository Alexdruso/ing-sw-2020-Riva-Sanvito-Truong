package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.ConnectToServerGUIClientState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.commons.lang.math.NumberUtils;

public class ConnectToServerController extends AbstractController{
    @FXML
    Button connectButton;
    @FXML
    TextField addressField;
    @FXML
    TextField portField;
    @FXML
    Label errorLabel;

    @FXML
    public void handleConnectButton(ActionEvent event){
        if(NumberUtils.isNumber(portField.getText()) &&
                addressField.getText().length() != 0 &&
                portField.getText().length() != 0){
            ((ConnectToServerGUIClientState)state).setHostPort(addressField.getText(), portField.getText());
        } else {
            errorLabel.setOpacity(1);
            errorLabel.setText("Invalid host or port!");
        }
    }

    @FXML
    public void handleMenuButton(ActionEvent event){
        ((ConnectToServerGUIClientState)state).returnToMenu();
    }

    @FXML
    public void initialize(){
        errorLabel.setOpacity(0);
    }

    public void handleError(String message){
        errorLabel.setOpacity(1);
        errorLabel.setText("Could not connect to the server!");
    }
}
