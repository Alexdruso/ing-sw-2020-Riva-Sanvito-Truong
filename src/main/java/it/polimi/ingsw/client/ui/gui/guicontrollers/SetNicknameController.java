package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.SetNicknameGUIClientState;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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

    final BooleanProperty firstShow = new SimpleBooleanProperty(true);

    @FXML
    public void handleMenuButton(ActionEvent event){
        ((SetNicknameGUIClientState)state).returnToMenu();
    }

    @FXML
    public void handleNicknameButton(ActionEvent event){
        if(nicknameField.getLength() != 0) {
            if(nicknameField.getLength() >= 30){
                //Just to not mess up the rendering of the nickname
                errorLabel.setText(I18n.string(I18nKey.ERROR_INVALID_NICKNAME_LENGTH));
                errorLabel.setOpacity(1);
            } else {
                ((SetNicknameGUIClientState) state).setNickname(nicknameField.getText());
            }
        } else {
            errorLabel.setText(I18n.string(I18nKey.ERROR_INVALID_NICKNAME_GENERIC));
            errorLabel.setOpacity(1);
        }
    }

    @Override
    public void onSceneShow(){
        firstShow.setValue(true);
        nicknameField.clear();
        errorLabel.setOpacity(0);
    }

    @FXML
    public void initialize(){
        nicknameField.focusedProperty().addListener((obs, oldValue, newValue) -> {
            if(newValue && firstShow.get()){
                setNicknameButton.requestFocus();
                firstShow.setValue(false);
            }
        });
    }

    @Override
    public void handleError(String message) {
        errorLabel.setText(I18n.string(I18nKey.ERROR_NICKNAME_TAKEN));
        errorLabel.setOpacity(1);
    }
}
