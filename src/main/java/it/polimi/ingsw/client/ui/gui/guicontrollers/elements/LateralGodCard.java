package it.polimi.ingsw.client.ui.gui.guicontrollers.elements;

import it.polimi.ingsw.client.ui.gui.JavaFXGUI;
import it.polimi.ingsw.client.ui.gui.utils.GodAsset;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import it.polimi.ingsw.utils.networking.transmittables.ReducedGod;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.NamedArg;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.CacheHint;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This graphical component can be used to show the name, description and card of a god in a scene
 */
public class LateralGodCard extends StackPane {
    private static final Logger LOGGER = Logger.getLogger(LateralGodCard.class.getName());

    private final Map<GodAsset, Image> cachedCards = new EnumMap<>(GodAsset.class);

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
    @FXML
    Label topLabel;

    TranslateTransition gradientPaneTransitionOut;
    TranslateTransition cardPaneTransitionOut;

    ParallelTransition sideBarTransitionOut;
    ParallelTransition sideBarTransitionIn;

    private ReducedGod currentGod;
    private boolean sideBarVisible = false;

    private final boolean hasButton;

    Consumer<ReducedGod> godSelectionCallback;

    /**
     * Class Constructor
     * @param hasButton true if there should be a button displayed under the god card
     */
    public LateralGodCard(@NamedArg("hasButton") boolean hasButton){
        this.hasButton = hasButton;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/LateralGodCard.fxml"), I18n.getResourceBundle());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * This method is used to set a callback in order for the controller that contains the LateralGodCard component
     * to receive information about whether the God has been selected.
     * @param callback the callback to be executed when the god has been selected
     */
    public void setGodSelectionCallback(Consumer<ReducedGod> callback){
        this.godSelectionCallback = callback;
    }

    /**
     * This method takes a list of ReducedGod and loads all the assets for the gods
     * @param gods the list of ReducedGod
     */
    public void setGods(List<ReducedGod> gods){
        for(ReducedGod god: gods){
            GodAsset ga = GodAsset.fromReducedGod(god);
            cachedCards.put(ga, new Image(getClass().getResourceAsStream(ga.cardLocation)));
        }
    }

    /**
     * This method sets the panel description text
     * @param description a String containing the description
     */
    public void setDescription(String description){
        topLabel.setText(description);
    }

    /**
     * This method is called when the button is pressed (if present).
     * It also retracts the LateralGodCard pane to the left side
     */
    @FXML
    public void selectGod(){
        if(godSelectionCallback != null){
            godSelectionCallback.accept(currentGod);
        }
        reverse();
    }

    /**
     * Retracts the LateralGodCard pane to the left side
     */
    @FXML
    public void reverse(){
        if(sideBarVisible){
            gradientPaneTransitionOut.setToX(-(gradientPane.getWidth()));
            cardPaneTransitionOut.setToX(-(cardPane.getWidth()));
            Platform.runLater(() -> sideBarTransitionOut.play());
            sideBarVisible = false;
        }
    }

    /**
     * JavaFX initialization method
     */
    @FXML
    void initialize(){
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

        if(hasButton){
            Button selectGodButton = new Button();
            selectGodButton.setPrefWidth(300);
            selectGodButton.setPrefHeight(55);
            selectGodButton.setText(I18n.string(I18nKey.SELECT_GOD_CARD));
            selectGodButton.getStyleClass().add("bigbutton");
            selectGodButton.setOnMouseClicked(e -> selectGod());
            cardPane.getChildren().add(selectGodButton);
            VBox.setMargin(selectGodButton, new Insets(0,0,0,30));
        }

        selectedCard.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(200), selectedCard);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        selectedCard.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(200), selectedCard);
            st.setToX(1);
            st.setToY(1);
            st.play();
        });
    }

    /**
     * This method shows the LateralGodCard pane and sets the appropriate text
     * @param reducedGod the ReducedGod that should be shown
     */
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
