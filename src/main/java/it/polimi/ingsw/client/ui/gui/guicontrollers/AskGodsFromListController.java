package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.AskGodsFromListGUIClientState;
import it.polimi.ingsw.client.ui.gui.utils.GodAsset;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

public class AskGodsFromListController extends AbstractController{
    final int ICONS_PER_ROW = 5;
    final double ICON_SPACING_H = 30;
    final double ICON_SPACING_V = 60;
    final double SCROLLPANE_INNER_PADDING = 30;

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
    Label godName;
    @FXML
    Label godSubtitle;
    @FXML
    Label godDescription;
    @FXML
    Label chooseGodsPrompt;

    TranslateTransition gradientPaneTransitionOut;
    TranslateTransition cardPaneTransitionOut;

    ParallelTransition sideBarTransitionOut;
    ParallelTransition sideBarTransitionIn;

    private boolean sideBarVisible = false;

    private ObservableList<Pane> godIcons = FXCollections.observableArrayList();

    private Map<GodAsset, Image> cachedCards = new HashMap<>();

    private Pane getIconPane(GodAsset ga){
        ImageView img = new ImageView(ga.iconLocation);
        img.setPreserveRatio(true);
        img.fitWidthProperty().bind(iconsPane.widthProperty()
                .subtract(ICONS_PER_ROW*ICON_SPACING_H + 2*SCROLLPANE_INNER_PADDING)
                .divide(ICONS_PER_ROW));
        img.setCache(true);

        Label label = new Label();

        label.setText(I18n.string(I18nKey.valueOf(ga.name.toUpperCase()+"_NAME")));
        //TODO: change text size dynamically
        label.getStyleClass().add("god-label");

        img.setOnMouseClicked((MouseEvent mouseEvent) -> {
            selectedCard.setImage(cachedCards.get(ga));
            godName.setText(I18n.string(I18nKey.valueOf(ga.name.toUpperCase()+"_NAME")));
            godSubtitle.setText(I18n.string(I18nKey.valueOf(ga.name.toUpperCase()+"_SUBTITLE")));
            godDescription.setText(I18n.string(I18nKey.valueOf(ga.name.toUpperCase()+"_DESCRIPTION")));
            sideBarTransitionIn.play();
            sideBarVisible = true;
        });

        VBox iconPane = new VBox(img, label);
        iconPane.setAlignment(Pos.CENTER);
        return iconPane;
    }

    @FXML
    public void handleMenuButton(ActionEvent event){
        ((AskGodsFromListGUIClientState)state).returnToMenu();
    }

    @FXML
    public void reverse(){
        if(sideBarVisible){
            gradientPaneTransitionOut.setToX(-(gradientPane.getWidth()));
            cardPaneTransitionOut.setToX(-(cardPane.getWidth()));
            Platform.runLater(sideBarTransitionOut::play);
            sideBarVisible = false;
        }
    }

    @Override
    public void handleError(String message) {

    }

    @Override
    public void setupController(){
        //int playersCount = client.getGame().getPlayersCount();
        //chooseGodsPrompt.setText(String.format(String.format("%s:", I18n.string(I18nKey.CHOOSE_D_GODS_THAT_WILL_BE_AVAILABLE)), playersCount));
        chooseGodsPrompt.setText("PLACEHOLDER TEXT");
    }

    @FXML
    public void initialize(){
        gradientPane.setCacheHint(CacheHint.SPEED);
        gradientPane.setTranslateX(-1920);
        cardPane.setCacheHint(CacheHint.SPEED);
        cardPane.setTranslateX(-1280);

        for(GodAsset ga: GodAsset.values()){
            cachedCards.put(ga, new Image(getClass().getResourceAsStream(ga.cardLocation)));
            godIcons.add(getIconPane(ga));
        }

        iconsPane.getChildren().addAll(godIcons);

        iconsPane.setHgap(ICON_SPACING_H);
        iconsPane.setVgap(ICON_SPACING_V);
        iconsPane.setPadding(new Insets(SCROLLPANE_INNER_PADDING,SCROLLPANE_INNER_PADDING,
                SCROLLPANE_INNER_PADDING, SCROLLPANE_INNER_PADDING));

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

        rootPane.widthProperty().addListener((o, oldWidth, newWidth) -> {
            if(!sideBarVisible){
                //Move side bar to just outside the screen
                gradientPane.setTranslateX(-1*(double)newWidth);
            }
        });
    }
}
