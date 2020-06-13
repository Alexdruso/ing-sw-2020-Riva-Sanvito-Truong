package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.AskGodFromListGUIClientState;
import it.polimi.ingsw.client.ui.gui.guicontrollers.elements.LateralGodCard;
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
    private static final double HBOX_INNER_PADDING = 30;

    private static final Logger LOGGER = Logger.getLogger(AskGodFromListController.class.getName());

    @FXML Pane rootPane;
    @FXML HBox godListPane;
    @FXML Label chooseGodsPrompt;

    private Map<ReducedGod, Pane> godIcons;
    private List<ReducedGod> gods;
    private LateralGodCard lateralGodCard;

    @FXML
    public void handleMenuButton(ActionEvent event){
        ((AskGodFromListGUIClientState)state).returnToMenu();
    }

    @Override
    public void handleError(String message) {
        LOGGER.log(Level.SEVERE, message);
    }

    public void removeGodIcon(ReducedGod reducedGod){
        gods.remove(reducedGod);
        Pane selectedGodPane = godIcons.get(reducedGod);
        godListPane.getChildren().remove(selectedGodPane);
        ((AskGodFromListGUIClientState)state).setChosenGod(reducedGod);
    }

    @Override
    public void setupController(){
        godIcons = new HashMap<>();
        //TODO: move this into FXML
        chooseGodsPrompt.setText(I18n.string(I18nKey.YOU_CAN_CHOOSE_ONE_OF_THESE_GODS));
        gods = new ArrayList<>(client.getGods());
        lateralGodCard.setGods(gods);
        for(ReducedGod god: gods){
            GodAsset ga = GodAsset.fromReducedGod(god);
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
        lateralGodCard = new LateralGodCard();
        lateralGodCard.setGodSelectionCallback(this::removeGodIcon);
        rootPane.getChildren().add(lateralGodCard);
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
            lateralGodCard.clickGod(god);
        });

        VBox iconPane = new VBox(img, label);
        iconPane.setAlignment(Pos.CENTER);
        return iconPane;
    }
}
