package it.polimi.ingsw.client.ui.gui.guicontrollers.elements;

import it.polimi.ingsw.client.ui.gui.utils.SceneLoader;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import javafx.beans.NamedArg;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerLateralLabel extends AnchorPane{
    private static final Logger LOGGER = Logger.getLogger(PlayerLateralLabel.class.getName());
    private StringProperty playerNameString = new SimpleStringProperty();
    private StringProperty godNameString = new SimpleStringProperty();

    @FXML AnchorPane container;
    @FXML Label godName;
    @FXML Label playerName;
    @FXML Label statusLabel;

    private boolean isSpectating = false;

    public PlayerLateralLabel(@NamedArg("playerName") String playerNameString, @NamedArg("godName") String godNameString) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/PlayerLateralLabel.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        setPlayerNameString(playerNameString);
        setGodNameString(godNameString);
    }


    public void setActiveStatus(boolean active){
        String toRemove, toAdd;

        if(active){
            toRemove = "player-lateral-label-passive";
            toAdd = "player-lateral-label-active";
            statusLabel.setText(I18n.string(I18nKey.CURRENTLY_PLAYING).toUpperCase());
        } else {
            toRemove = "player-lateral-label-active";
            toAdd = "player-lateral-label-passive";
            if(!isSpectating){
                statusLabel.setText(I18n.string(I18nKey.WAITING_FOR_THEIR_TURN).toUpperCase());
            }
        }

        int index = container.getStyleClass().indexOf(toRemove);
        if(index != -1){
            container.getStyleClass().remove(index);
        }
        if(!container.getStyleClass().contains(toAdd)){
            container.getStyleClass().add(toAdd);
        }

    }

    public void setSpectatingStatus(){
        isSpectating = true;
        statusLabel.setText(I18n.string(I18nKey.SPECTATING).toUpperCase());
    }

    @FXML private void initialize(){
        playerName.textProperty().bind(playerNameString);
        godName.textProperty().bind(godNameString);
    }

    //Here follows a few "useless" functions which are used behind the scenes by JavaFX

    public String getPlayerNameString(){
        return playerNameString.get();
    }

    public void setPlayerNameString(String value){
        this.playerNameString.set(value);
    }

    public StringProperty playerNameStringProperty(){
        return playerNameString;
    }

    public String getGodNameString(){
        return godNameString.get();
    }

    public void setGodNameString(String value){
        this.godNameString.set(value);
    }

    public StringProperty godNameStringProperty(){
        return godNameString;
    }
}

