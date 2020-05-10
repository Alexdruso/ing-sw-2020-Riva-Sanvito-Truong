package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.AskGodsFromListGUIClientState;
import it.polimi.ingsw.client.ui.gui.utils.GodAsset;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AskGodsFromListController extends AbstractController{
    final int ICONS_PER_ROW = 5;
    final double ICON_SPACING_H = 30;
    final double ICON_SPACING_V = 30;

    @FXML
    Pane rootPane;
    @FXML
    Pane gradientPane;
    @FXML
    Pane cardPane;
    @FXML
    FlowPane iconsPane;
    @FXML
    ImageView selectedCard;
    @FXML
    VBox currentCardAndButton;

    TranslateTransition gradientPaneTransitionOut;
    TranslateTransition cardPaneTransitionOut;

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
    public void reverse(){
        if(sideBarVisible){
            System.out.println("Clicking to reverse");
            gradientPaneTransitionOut.setToX(-(gradientPane.getWidth()));
            cardPaneTransitionOut.setToX(-(cardPane.getWidth()));
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
        gradientPane.setTranslateX(-1920);
        cardPane.setCacheHint(CacheHint.SPEED);
        cardPane.setTranslateX(-1280);

        for(GodAsset ga: GodAsset.values()){
            ImageView img = new ImageView(ga.iconLocation);
            img.setPreserveRatio(true);
            img.fitWidthProperty().bind(iconsPane.widthProperty()
                    .subtract(ICONS_PER_ROW*ICON_SPACING_H)
                    .divide(ICONS_PER_ROW));
            img.setCache(true);
            cachedCards.put(ga, new Image(getClass().getResourceAsStream(ga.cardLocation)));
            img.setOnMouseClicked((MouseEvent mouseEvent) -> {
                selectedCard.setImage(cachedCards.get(ga));
                sideBarTransitionIn.play();
                sideBarVisible = true;
            });
            godIcons.add(img);
        }

        iconsPane.getChildren().addAll(godIcons);

        iconsPane.setHgap(ICON_SPACING_H);
        iconsPane.setVgap(ICON_SPACING_V);

        gradientPaneTransitionOut = new TranslateTransition(Duration.millis(400), gradientPane);
        cardPaneTransitionOut = new TranslateTransition(Duration.millis(200), cardPane);
        //TODO: fix this hack and animate by width instead of fixed length
        PauseTransition delayTransition = new PauseTransition(Duration.millis(200));
        SequentialTransition pausingTransition = new SequentialTransition(delayTransition, gradientPaneTransitionOut);
        sideBarTransitionOut = new ParallelTransition(cardPaneTransitionOut, pausingTransition);
        sideBarTransitionOut.setInterpolator(Interpolator.LINEAR);

        TranslateTransition gradientPaneTransitionIn = new TranslateTransition(Duration.millis(400), gradientPane);
        TranslateTransition cardPaneTransitionIn = new TranslateTransition(Duration.millis(200), cardPane);
        gradientPaneTransitionIn.setToX(0);
        cardPaneTransitionIn.setToX(0);
        delayTransition = new PauseTransition(Duration.millis(200));
        pausingTransition = new SequentialTransition(delayTransition, cardPaneTransitionIn);
        sideBarTransitionIn = new ParallelTransition(gradientPaneTransitionIn, pausingTransition);
        sideBarTransitionIn.setInterpolator(Interpolator.LINEAR);

        rootPane.widthProperty().addListener((o, oldWidth, newWidth) -> {
            if(!sideBarVisible){
                //Move side bar to just outside the screen
                gradientPane.setTranslateX(-1*(double)newWidth);
            }
        });
    }
}
