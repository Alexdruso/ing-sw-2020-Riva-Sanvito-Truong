package it.polimi.ingsw.client.ui.gui.guicontrollers.elements;

import it.polimi.ingsw.client.ui.gui.JavaFXGUI;
import it.polimi.ingsw.client.ui.gui.utils.GodAsset;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import it.polimi.ingsw.utils.networking.transmittables.ReducedGod;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.CacheHint;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LateralGodCard extends StackPane {
    private static final Logger LOGGER = Logger.getLogger(LateralGodCard.class.getName());

    private Map<GodAsset, Image> cachedCards = new EnumMap<>(GodAsset.class);

    @FXML
    Pane rootPane;
    @FXML
    Pane gradientPane;
    @FXML
    ImageView selectedCard;
    @FXML
    Label godName;
    @FXML
    Label godSubtitle;
    @FXML
    Label godDescription;
    @FXML
    Pane cardPane;
    @FXML
    HBox godText;

    TranslateTransition gradientPaneTransitionOut;
    TranslateTransition cardPaneTransitionOut;

    ParallelTransition sideBarTransitionOut;
    ParallelTransition sideBarTransitionIn;

    private ReducedGod currentGod;
    private boolean sideBarVisible = false;

    Consumer<ReducedGod> godSelectionCallback;

    public LateralGodCard(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/LateralGodCard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void setGodSelectionCallback(Consumer<ReducedGod> callback){
        this.godSelectionCallback = callback;
    }

    public void setGods(List<ReducedGod> gods){
        for(ReducedGod god: gods){
            GodAsset ga = GodAsset.fromReducedGod(god);
            cachedCards.put(ga, new Image(getClass().getResourceAsStream(ga.cardLocation)));
        }
    }

    @FXML
    public void selectGod(ActionEvent event){
        if(godSelectionCallback != null){
            godSelectionCallback.accept(currentGod);
        }
        reverse();
    }

    @FXML
    public void reverse(){
        if(sideBarVisible){
            gradientPaneTransitionOut.setToX(-(gradientPane.getWidth()));
            cardPaneTransitionOut.setToX(-(cardPane.getWidth()));
            Platform.runLater(() -> {
                sideBarTransitionOut.play();
            });
            sideBarVisible = false;
        }
    }

    @FXML
    public void initialize(){
        rootPane.setPickOnBounds(false);
        godText.setMouseTransparent(true);

        gradientPane.setOnMouseClicked(e -> reverse());

        gradientPane.setCacheHint(CacheHint.SPEED);
        gradientPane.setTranslateX(-1920);
        cardPane.setCacheHint(CacheHint.SPEED);
        cardPane.setTranslateX(-1280);

        gradientPaneTransitionOut = new TranslateTransition(Duration.millis(400), gradientPane);
        cardPaneTransitionOut = new TranslateTransition(Duration.millis(200), cardPane);
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

        JavaFXGUI.getPrimaryScene().widthProperty().addListener((o, oldWidth, newWidth) -> {
            if(!sideBarVisible){
                //Move side bar to just outside the screen
                gradientPane.setTranslateX(-1*(double)newWidth);
            }
        });
    }

    public void clickGod(ReducedGod reducedGod){
        currentGod = reducedGod;
        GodAsset ga = GodAsset.fromReducedGod(reducedGod);
        selectedCard.setImage(cachedCards.get(ga));
        godName.setText(I18n.string(I18nKey.valueOf(ga.godName.toUpperCase()+"_NAME")));
        godSubtitle.setText(I18n.string(I18nKey.valueOf(ga.godName.toUpperCase()+"_SUBTITLE")));
        godDescription.setText(I18n.string(I18nKey.valueOf(ga.godName.toUpperCase()+"_DESCRIPTION")));
        sideBarTransitionIn.play();
        sideBarVisible = true;
    }
}
