package it.polimi.ingsw.client.ui.gui.guicontrollers.elements;

import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import javafx.beans.NamedArg;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This graphical component can be used to show the players currently playing in a match
 */
public class PlayerLateralLabel extends AnchorPane{
    private static final Logger LOGGER = Logger.getLogger(PlayerLateralLabel.class.getName());
    private StringProperty playerNameProperty = new SimpleStringProperty();
    private StringProperty godNameProperty = new SimpleStringProperty();
    private IntegerProperty playerNumberProperty = new SimpleIntegerProperty();

    @FXML AnchorPane container;
    @FXML Label godName;
    @FXML Label playerName;
    @FXML Label statusLabel;
    @FXML Circle colorTip;
    @FXML Label colorLabel;

    /**
     * An enum that represents the color assigned to each of the three players
     */
    enum Colors {
        PLAYER1("133C55"),
        PLAYER2("F2A541"),
        PLAYER3("353535");

        /**
         * This attribute is a hex string representing the color
         */
        public final String color;

        /**
         * This method sets the color to the player
         * @param color a hex string representing the color
         */
        Colors(String color){
            this.color = color;
        }
    }

    /**
     * Class Constructor for a single PlayerLateralLabel
     * @param playerNameProperty a String containing the name of the player
     * @param playerNumberProperty an Integer containing the position of the player in the playing order
     * @param godNameProperty a String containing the player's God name
     */
    public PlayerLateralLabel(@NamedArg("playerName") String playerNameProperty,
                              @NamedArg("playerNumber") Integer playerNumberProperty,
                              @NamedArg("godName") String godNameProperty) {

        this.playerNameProperty.set(playerNameProperty);
        this.playerNumberProperty.set(playerNumberProperty);
        this.godNameProperty.set(I18n.string(I18nKey.valueOf(godNameProperty.toUpperCase()+"_NAME")));

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/PlayerLateralLabel.fxml"), I18n.getResourceBundle());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }


    /**
     * This method sets the graphical status of a label, depending on the value of active
     * @param active if true, the label is set to active. If false, the label is set to inactive.
     */
    public void setActiveStatus(boolean active){
        String toRemove;
        String toAdd;

        if(active){
            toRemove = "player-lateral-label-passive";
            toAdd = "player-lateral-label-active";
            statusLabel.setText(I18n.string(I18nKey.CURRENTLY_PLAYING).toUpperCase());
        } else {
            toRemove = "player-lateral-label-active";
            toAdd = "player-lateral-label-passive";
            statusLabel.setText(I18n.string(I18nKey.WAITING_FOR_THEIR_TURN).toUpperCase());
        }

        int index = container.getStyleClass().indexOf(toRemove);
        if(index != -1){
            container.getStyleClass().remove(index);
        }
        if(!container.getStyleClass().contains(toAdd)){
            container.getStyleClass().add(toAdd);
        }

    }

    /**
     * This method sets the graphical status of a label to Spectating
     */
    public void setSpectatingStatus(){
        setActiveStatus(false);
        statusLabel.setText(I18n.string(I18nKey.SPECTATING).toUpperCase());
        colorLabel.setOpacity(0);
        colorTip.setOpacity(0);
        godName.setOpacity(0);
    }

    /**
     * JavaFX initialization method
     */
    @FXML private void initialize(){
        playerName.textProperty().bind(playerNameProperty);
        godName.textProperty().bind(godNameProperty);
        colorLabel.setText(I18n.string(I18nKey.COLOR));
        colorTip.setFill(Paint.valueOf(Colors.values()[playerNumberProperty.get()].color));
    }
}

