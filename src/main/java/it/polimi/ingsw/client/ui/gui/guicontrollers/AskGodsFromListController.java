package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.AskGodsFromListGUIClientState;
import it.polimi.ingsw.client.ui.gui.guicontrollers.elements.LateralGodCard;
import it.polimi.ingsw.client.ui.gui.utils.GodAsset;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import it.polimi.ingsw.utils.networking.transmittables.ReducedGod;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class AskGodsFromListController extends AbstractController {
    private static final int ICONS_PER_ROW = 5;
    private static final double ICON_SPACING_H = 30;
    private static final double ICON_SPACING_V = 60;
    private static final double SCROLLPANE_INNER_PADDING = 30;
    private static final Logger LOGGER = Logger.getLogger(AskGodsFromListController.class.getName());

    @FXML
    Pane rootPane;
    @FXML
    FlowPane iconsPane;
    @FXML
    Label chooseGodsPrompt;

    LateralGodCard lateralGodCard;

    private Map<ReducedGod, Pane> godIcons = new HashMap<>();
    private List<ReducedGod> gods;


    @FXML
    public void handleMenuButton(ActionEvent event){
        ((AskGodsFromListGUIClientState)state).returnToMenu();
    }

    @Override
    public void handleError(String message) {
        LOGGER.log(Level.SEVERE, message);
       //Nothing to handle
    }

    public void removeGodIcon(ReducedGod reducedGod){
        gods.remove(reducedGod);
        Pane selectedGodPane = godIcons.get(reducedGod);
        iconsPane.getChildren().remove(selectedGodPane);
        ((AskGodsFromListGUIClientState)state).addChosenGod(reducedGod);
    }

    @Override
    public void setupController(){
        int playersCount = client.getGame().getPlayersCount();
        chooseGodsPrompt.setText(String.format(String.format("%s:", I18n.string(I18nKey.CHOOSE_D_GODS_THAT_WILL_BE_AVAILABLE)), playersCount));
        gods = new ArrayList<>(client.getGods());
        lateralGodCard.setGods(gods);
        for(ReducedGod god: gods){
            GodAsset ga = GodAsset.fromReducedGod(god);
            godIcons.put(god, getIconPane(ga, god));
        }
        iconsPane.getChildren().clear();
        iconsPane.getChildren().addAll(godIcons.entrySet()
                .stream()
                .sorted(Comparator.comparing(o -> o.getKey().getName()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList()));
    }

    @FXML
    public void initialize(){
        lateralGodCard = new LateralGodCard(true);
        lateralGodCard.setGodSelectionCallback(this::removeGodIcon);
        lateralGodCard.setDescription(I18n.string(I18nKey.GOD_DESCRIPTION));
        rootPane.getChildren().add(lateralGodCard);

        iconsPane.setHgap(ICON_SPACING_H);
        iconsPane.setVgap(ICON_SPACING_V);
        iconsPane.setPadding(new Insets(SCROLLPANE_INNER_PADDING,SCROLLPANE_INNER_PADDING,
                SCROLLPANE_INNER_PADDING, SCROLLPANE_INNER_PADDING));
    }

    private Pane getIconPane(GodAsset ga, ReducedGod god){
        ImageView img = new ImageView(ga.iconLocation);
        img.setPreserveRatio(true);
        img.fitWidthProperty().bind(iconsPane.widthProperty()
                .subtract(ICONS_PER_ROW*ICON_SPACING_H + 2*SCROLLPANE_INNER_PADDING)
                .divide(ICONS_PER_ROW));
        img.setCache(true);

        Label label = new Label();

        label.setText(I18n.string(I18nKey.valueOf(ga.godName.toUpperCase()+"_NAME")));
        //TODO: change text size dynamically
        label.getStyleClass().add("god-label");

        img.setOnMouseClicked((MouseEvent mouseEvent) -> lateralGodCard.clickGod(god));

        VBox iconPane = new VBox(img, label);
        iconPane.setAlignment(Pos.CENTER);

        return iconPane;
    }
}
