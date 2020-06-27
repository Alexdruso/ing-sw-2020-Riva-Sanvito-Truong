package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.WaitPlayersGUIClientState;
import it.polimi.ingsw.client.ui.gui.utils.AnimationHelper;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the WaitPlayers State
 */
public class WaitPlayersController extends AbstractController{
    private final Image[] imageArray = new Image[4];
    private static final Logger LOGGER = Logger.getLogger(WaitPlayersController.class.getName());

    @FXML
    Label waitPromptLabel;

    @FXML
    ImageView loadingTower;

    AnimationHelper animationHelper;

    @FXML
    public void handleMenuButton(ActionEvent event){
        ((WaitPlayersGUIClientState)state).returnToMenu();
    }

    @Override
    public void setupController(){
        waitPromptLabel.setText(I18n.string(I18nKey.WAITING_FOR_THE_OTHER_PLAYERS));
        animationHelper.animateLoadingScreen(imageArray, loadingTower);
    }

    @FXML
    public void initialize(){
        imageArray[0] = new Image("assets/loading_tower0_400px.png");
        imageArray[1] = new Image("assets/loading_tower1_400px.png");
        imageArray[2] = new Image("assets/loading_tower2_400px.png");
        imageArray[3] = new Image("assets/loading_tower3_400px.png");
        animationHelper = new AnimationHelper();
    }

    @Override
    public void handleError(String message) {
        LOGGER.log(Level.SEVERE, message);
    }

    public void stopAnimation(){
        animationHelper.stopAnimations();
    }
}
