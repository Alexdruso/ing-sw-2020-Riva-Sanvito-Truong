package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.AskGodFromListGUIClientState;
import it.polimi.ingsw.client.ui.gui.utils.GodAsset;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import it.polimi.ingsw.utils.networking.transmittables.ReducedGod;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class AskGodFromListController extends AbstractController{
    private static final Logger LOGGER = Logger.getLogger(AskGodFromListController.class.getName());

    @FXML Pane rootPane;
    @FXML Pane gradientPane;
    @FXML Pane cardPane;
    @FXML HBox godListPane;
    @FXML ImageView selectedCard;
    @FXML Label godName;
    @FXML Label godSubtitle;
    @FXML Label godDescription;
    @FXML Label chooseGodsPrompt;

    TranslateTransition gradientPaneTransitionOut;
    TranslateTransition cardPaneTransitionOut;

    ParallelTransition sideBarTransitionOut;
    ParallelTransition sideBarTransitionIn;

    private boolean sideBarVisible = false;

    private Map<ReducedGod, Pane> godIcons;
    private Map<GodAsset, Image> cachedCards = new EnumMap<>(GodAsset.class);
    private List<ReducedGod> gods;

    private ReducedGod currentGod;

    @FXML
    public void selectGod(ActionEvent event){
        gods.remove(currentGod);
        Pane selectedGodPane = godIcons.get(currentGod);
        godListPane.getChildren().remove(selectedGodPane);
        ((AskGodFromListGUIClientState)state).setChosenGod(currentGod);
        reverse();
    }

    @FXML
    public void handleMenuButton(ActionEvent event){
        ((AskGodFromListGUIClientState)state).returnToMenu();
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
        LOGGER.log(Level.SEVERE, message);
    }

    @Override
    public void setupController(){
        godIcons = new HashMap<>();
        chooseGodsPrompt.setText(I18n.string(I18nKey.YOU_CAN_CHOOSE_ONE_OF_THESE_GODS));
        gods = new ArrayList<>(client.getGods());
        for(ReducedGod god: gods){
            GodAsset ga = GodAsset.fromReducedGod(god);
            cachedCards.put(ga, new Image(getClass().getResourceAsStream(ga.cardLocation)));
            godIcons.put(god, getIconPane(ga, god));
        }
        godListPane.getChildren().clear();
        godListPane.getChildren().addAll(godIcons.entrySet()
                .stream()
                .sorted(Comparator.comparing(o -> o.getKey().name))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList()));
    }

    @FXML
    public void initialize(){
        gradientPane.setCacheHint(CacheHint.SPEED);
        gradientPane.setTranslateX(-1920);
        cardPane.setCacheHint(CacheHint.SPEED);
        cardPane.setTranslateX(-1280);

        //TODO: Bring all of this to the AnimationLoader

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

    private Pane getIconPane(GodAsset ga, ReducedGod god){
        ImageView img = new ImageView(ga.cardLocation);
        img.setPreserveRatio(true);
        img.setCache(true);
        img.setFitWidth(200);

        Label label = new Label();

        label.setText(I18n.string(I18nKey.valueOf(ga.godName.toUpperCase()+"_NAME")));
        //TODO: change text size dynamically
        label.getStyleClass().add("god-label");

        img.setOnMouseClicked((MouseEvent mouseEvent) -> {
            currentGod = god;
            selectedCard.setImage(cachedCards.get(ga));
            godName.setText(I18n.string(I18nKey.valueOf(ga.godName.toUpperCase()+"_NAME")));
            godSubtitle.setText(I18n.string(I18nKey.valueOf(ga.godName.toUpperCase()+"_SUBTITLE")));
            godDescription.setText(I18n.string(I18nKey.valueOf(ga.godName.toUpperCase()+"_DESCRIPTION")));
            sideBarTransitionIn.play();
            sideBarVisible = true;
        });

        VBox iconPane = new VBox(img, label);
        iconPane.setAlignment(Pos.CENTER);
        return iconPane;
    }
}
