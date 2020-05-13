package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.WaitPlayersGUIClientState;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class AskGodFromListPassiveController extends AbstractController{
    private final Image[] imageArray = new Image[4];

    @FXML
    Label waitPromptLabel;

    @FXML
    ImageView loadingTower;

    Timer animationTimer;

    @FXML
    public void handleMenuButton(ActionEvent event){
        ((WaitPlayersGUIClientState)state).returnToMenu();
    }

    @Override
    public void setupController(){
        waitPromptLabel.setText(String.format(
                I18n.string(I18nKey.WAIT_FOR_S_TO_CHOOSE_THEIR_GOD),
                client.getCurrentActiveUser().nickname));

        final int[] imgCount = {0};
        animationTimer = new Timer();
        //FIXME: I believe that this timer keeps the Client from shutting down when closing the window
        //Maybe submit all Timer Tasks to a global instance of Timer so it can stop all of them?
        animationTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> loadingTower.setImage(imageArray[imgCount[0]]));
                if(imgCount[0] == 3){
                    imgCount[0] = 0;
                } else {
                    imgCount[0]++;
                }
            }
        }, 0, 1000);

    }

    @FXML
    public void initialize(){
        imageArray[0] = new Image("assets/loading_tower0_400px.png");
        imageArray[1] = new Image("assets/loading_tower1_400px.png");
        imageArray[2] = new Image("assets/loading_tower2_400px.png");
        imageArray[3] = new Image("assets/loading_tower3_400px.png");
    }

    public void stopAnimation(){
        animationTimer.cancel();
    }

    @Override
    public void handleError(String message) {
        //No error to handle
    }
}
