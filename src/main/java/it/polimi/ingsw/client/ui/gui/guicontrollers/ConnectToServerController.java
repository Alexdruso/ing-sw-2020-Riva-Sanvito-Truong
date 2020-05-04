package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.ConnectToServerGUIClientState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    public void handleConnectButton(ActionEvent event){
        if(NumberUtils.isNumber(portField.getText()) &&
                addressField.getText().length() != 0 &&
                portField.getText().length() != 0){
            ((ConnectToServerGUIClientState)state).setHostPort(addressField.getText(), portField.getText());
        }
    }

    @FXML
    public void initialize(){
    }
}
