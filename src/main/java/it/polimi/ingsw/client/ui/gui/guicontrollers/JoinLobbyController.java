package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.JoinLobbyGUIClientState;
import it.polimi.ingsw.client.ui.gui.utils.AnimationHelper;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class JoinLobbyController extends AbstractController {

    private final Image[] imageArray = new Image[4];

    @FXML
    Label welcomeLabel;

    @FXML
    ImageView loadingTower;

    AnimationHelper animationHelper;

    @FXML
    public void handleMenuButton(ActionEvent event){
        ((JoinLobbyGUIClientState)state).returnToMenu();
    }

    @Override
    public void handleError(String message) {

    }

    @Override
    public void setupController() {
        welcomeLabel.setText(String.format(I18n.string(I18nKey.WELCOME_TO_THE_SERVER_S), client.getNickname()));
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

    public void stopAnimation(){
        animationHelper.stopAnimations();
    }
}
