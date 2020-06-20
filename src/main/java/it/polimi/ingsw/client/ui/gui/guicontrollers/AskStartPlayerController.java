package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.reducedmodel.ReducedPlayer;
import it.polimi.ingsw.client.ui.gui.AskStartPlayerGUIClientState;
import it.polimi.ingsw.utils.networking.transmittables.ReducedUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class AskStartPlayerController extends AbstractController{

    private static final Logger LOGGER = Logger.getLogger(AskStartPlayerController.class.getName());
    @FXML
    VBox playerButtonsPane;

    @FXML
    Label errorLabel;

    @Override
    public void setupController(){
        playerButtonsPane.getChildren().clear();
        errorLabel.setOpacity(0);
        List<ReducedUser> users = client.getGame()
                .getPlayersList()
                .stream()
                .map(ReducedPlayer::getUser)
                .collect(Collectors.toList());

        for(ReducedUser user: users){
            Button userButton = new Button();
            userButton.setText(user.getNickname());
            userButton.getStyleClass().add("bigbutton");
            userButton.setMaxWidth(400);
            userButton.setPrefWidth(400);
            userButton.setPrefHeight(60);
            userButton.setOnAction(e -> ((AskStartPlayerGUIClientState)state).selectUser(user));
            playerButtonsPane.getChildren().add(userButton);
        }
    }

    @FXML
    public void handleMenuButton(ActionEvent event){
        ((AskStartPlayerGUIClientState)state).returnToMenu();
    }

    @Override
    public void handleError(String message) {
        LOGGER.log(Level.SEVERE, message);

    }
}
