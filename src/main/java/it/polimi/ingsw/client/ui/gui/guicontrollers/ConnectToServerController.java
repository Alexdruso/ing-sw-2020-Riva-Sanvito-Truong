package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.ConnectToServerGUIClientState;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.commons.lang.math.NumberUtils;

/**
 * Controller for the ConnectToServer state
 */
public class ConnectToServerController extends AbstractController{
    @FXML
    Button connectButton;
    @FXML
    TextField hostField;
    @FXML
    TextField portField;
    @FXML
    Label errorLabel;

    private final BooleanProperty firstShow = new SimpleBooleanProperty(true);

    @FXML
    public void handleConnectButton(ActionEvent event){
        String host = "127.0.0.1";
        String port = "7268";
        if(hostField.getText().length() != 0) host = hostField.getText();
        if(portField.getText().length() != 0) {
            if(NumberUtils.isNumber(portField.getText())){
                port = portField.getText();
            } else {
                errorLabel.setOpacity(1);
                errorLabel.setText(I18n.string(I18nKey.ERROR_INVALID_PORT));
                return;
            }
        }
        ((ConnectToServerGUIClientState)state).setHostPort(host, port);
    }

    @FXML
    public void handleMenuButton(ActionEvent event){
        ((ConnectToServerGUIClientState)state).returnToMenu();
    }

    @Override
    public void onSceneShow(){
        firstShow.setValue(true);
        errorLabel.setOpacity(0);
    }

    @FXML
    public void initialize(){
        errorLabel.setOpacity(0);
        hostField.focusedProperty().addListener((obs, oldValue, newValue) -> {
            if(Boolean.TRUE.equals(newValue) && firstShow.get()){
                connectButton.requestFocus();
                firstShow.setValue(false);
            }
        });
    }

    public void handleError(String message){
        errorLabel.setOpacity(1);
        errorLabel.setText(message);
    }
}
