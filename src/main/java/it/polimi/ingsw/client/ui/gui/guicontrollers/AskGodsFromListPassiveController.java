package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.guicontrollers.elements.MenuConfirmation;
import it.polimi.ingsw.client.ui.gui.utils.AnimationHelper;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the AskGodsFromList state, passive version
 */
public class AskGodsFromListPassiveController extends AbstractController{
    private final Image[] imageArray = new Image[4];
    private static final Logger LOGGER = Logger.getLogger(AskGodsFromListPassiveController.class.getName());

    @FXML
    Label waitPromptLabel;

    @FXML
    ImageView loadingTower;

    AnimationHelper animationHelper;

    /**
     * Handles menu button on screen
     * @param event the mouse click event
     */
    @FXML
    void handleMenuButton(ActionEvent event){
        MenuConfirmation.showMenuConfirmation(client);
    }

    @Override
    public void setupController(){
        waitPromptLabel.setText(
                String.format(
                        I18n.string(I18nKey.WAIT_FOR_S_TO_CHOOSE_THE_GODS),
                        client.getCurrentActiveUser().getNickname()
                )
        );
        waitPromptLabel.setAlignment(Pos.CENTER);

        animationHelper.animateLoadingScreen(imageArray, loadingTower);
    }

    /**
     * JavaFX initialization method
     */
    @FXML
    void initialize(){
        imageArray[0] = new Image("assets/loading_tower0_400px.png");
        imageArray[1] = new Image("assets/loading_tower1_400px.png");
        imageArray[2] = new Image("assets/loading_tower2_400px.png");
        imageArray[3] = new Image("assets/loading_tower3_400px.png");
        animationHelper = new AnimationHelper();
    }

    /**
     * This method stops the AnimationHelper
     */
    public void stopAnimation(){
        animationHelper.stopAnimations();
    }

    @Override
    public void handleError(String message) {
        LOGGER.log(Level.SEVERE, message);
        //No error to handle
    }
}
