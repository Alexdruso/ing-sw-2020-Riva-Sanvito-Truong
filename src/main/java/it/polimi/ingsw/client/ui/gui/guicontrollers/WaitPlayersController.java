package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.JoinLobbyGUIClientState;
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

public class WaitPlayersController extends AbstractController{
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
        waitPromptLabel.setText(I18n.string(I18nKey.WAITING_FOR_THE_OTHER_PLAYERS));
        final int[] imgCount = {0};
        animationTimer = new Timer(true);
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

    @Override
    public void handleError(String message) {

    }

    public void stopAnimation(){
        animationTimer.cancel();
    }
}
