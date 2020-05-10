package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.AskGodsFromListGUIClientState;
import it.polimi.ingsw.client.ui.gui.utils.GodAsset;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AskGodsFromListController extends AbstractController{

    @FXML
    Pane gradientPane;
    @FXML
    Pane cardPane;
    @FXML
    FlowPane iconsPane;
    @FXML
    ImageView selectedCard;

    ParallelTransition sideBarTransitionOut;
    ParallelTransition sideBarTransitionIn;

    private boolean sideBarVisible = false;

    private ArrayList<ImageView> godIcons = new ArrayList<>();
    private Map<GodAsset, Image> cachedCards = new HashMap<>();

    @FXML
    public void handleMenuButton(ActionEvent event){
        ((AskGodsFromListGUIClientState)state).returnToMenu();
    }

    @FXML
    public void test(ActionEvent event){
        System.out.println("TEST");
        Platform.runLater(sideBarTransitionIn::play);
    }

    @FXML
    public void reverse(){
        if(sideBarVisible){
            Platform.runLater(sideBarTransitionOut::play);
            sideBarVisible = false;
        }
    }

    @Override
    public void handleError(String message) {

    }

    @FXML
    public void initialize(){
        gradientPane.setCacheHint(CacheHint.SPEED);
        //gradientPane.setTranslateX(-1920);
        cardPane.setCacheHint(CacheHint.SPEED);
        //cardPane.setTranslateX(-1280);
        gradientPane.layoutXProperty().bind()

        for(GodAsset ga: GodAsset.values()){
            ImageView img = new ImageView(ga.iconLocation);
            img.setPreserveRatio(true);
            img.setFitHeight(350);
            img.setFitWidth(350);
            img.setCache(true);
            cachedCards.put(ga, new Image(getClass().getResourceAsStream(ga.cardLocation)));
            img.setOnMouseClicked((MouseEvent mouseEvent) -> {
                Platform.runLater(() -> {
                    selectedCard.setImage(cachedCards.get(ga));
                    sideBarTransitionIn.play();
                    sideBarVisible = true;
                });
            });
            godIcons.add(img);
        }

        iconsPane.getChildren().addAll(godIcons);

        TranslateTransition gradientPaneTransition = new TranslateTransition(Duration.millis(400), gradientPane);
        TranslateTransition cardPaneTransition = new TranslateTransition(Duration.millis(200), cardPane);
        //TODO: fix this hack and animate by width instead of fixed length
        //gradientPaneTransition.setFromX(0);
        gradientPaneTransition.setByX(-1920);
        //cardPaneTransition.setFromX(0);
        cardPaneTransition.setByX(-400);
        PauseTransition delayTransition = new PauseTransition(Duration.millis(200));
        SequentialTransition pausingTransition = new SequentialTransition(delayTransition, gradientPaneTransition);
        sideBarTransitionOut = new ParallelTransition(cardPaneTransition, pausingTransition);
        sideBarTransitionOut.setInterpolator(Interpolator.LINEAR);

        gradientPaneTransition = new TranslateTransition(Duration.millis(400), gradientPane);
        cardPaneTransition = new TranslateTransition(Duration.millis(200), cardPane);
        //gradientPaneTransition.setFromX(-1280);
        gradientPaneTransition.setToX(0);
        //cardPaneTransition.setFromX(-370);
        cardPaneTransition.setToX(0);
        delayTransition = new PauseTransition(Duration.millis(200));
        pausingTransition = new SequentialTransition(delayTransition, cardPaneTransition);
        sideBarTransitionIn = new ParallelTransition(gradientPaneTransition, pausingTransition);
        sideBarTransitionIn.setInterpolator(Interpolator.LINEAR);
    }
}
