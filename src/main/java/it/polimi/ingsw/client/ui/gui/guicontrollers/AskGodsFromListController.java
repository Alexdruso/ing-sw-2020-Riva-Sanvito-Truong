package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.AskGodsFromListGUIClientState;
import it.polimi.ingsw.client.ui.gui.JavaFXGUI;
import it.polimi.ingsw.client.ui.gui.guicontrollers.elements.LateralGodCard;
import it.polimi.ingsw.client.ui.gui.utils.GodAsset;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import it.polimi.ingsw.utils.networking.transmittables.ReducedGod;
import javafx.animation.ScaleTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
import javafx.util.Duration;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Controller for the AskGodsFromList state
 */
public class AskGodsFromListController extends AbstractController {
    private static final int ICONS_PER_ROW = 5;
    private static final double ICON_SPACING_H = 30;
    private static final double ICON_SPACING_V = 60;
    private static final double SCROLLPANE_INNER_PADDING = 30;
    private static final double FONT_SIZE_RATIO = 100;
    private static final Logger LOGGER = Logger.getLogger(AskGodsFromListController.class.getName());

    @FXML
    Pane rootPane;
    @FXML
    FlowPane iconsPane;
    @FXML
    Label chooseGodsPrompt;

    LateralGodCard lateralGodCard;
    private DoubleProperty fontSize = new SimpleDoubleProperty(10);

    private Map<ReducedGod, Pane> godIcons = new HashMap<>();
    private List<ReducedGod> gods;


    @FXML
    void handleMenuButton(ActionEvent event){
        ((AskGodsFromListGUIClientState)state).returnToMenu();
    }

    @Override
    public void handleError(String message) {
        LOGGER.log(Level.SEVERE, message);
       //Nothing to handle
    }

    /**
     * This method is a callback for the LateralGodCard in order to remove a selected god from the selection screen
     * @param reducedGod the ReducedGod instance representing the God to be removed
     */
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
        gods = new ArrayList<>(client.getGodsAvailableForChoice());
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
    void initialize(){
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
        img.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(200), img);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });
        img.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(200), img);
            st.setToX(1);
            st.setToY(1);
            st.play();
        });

        Label label = new Label();

        label.setText(I18n.string(I18nKey.valueOf(ga.godName.toUpperCase()+"_NAME")));
        fontSize.bind(JavaFXGUI.getPrimaryScene().widthProperty().add(JavaFXGUI.getPrimaryStage().heightProperty()).divide(FONT_SIZE_RATIO));
        label.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString()));
        label.getStyleClass().add("god-label");

        img.setOnMouseClicked((MouseEvent mouseEvent) -> lateralGodCard.clickGod(god));

        VBox iconPane = new VBox(img, label);
        iconPane.setAlignment(Pos.CENTER);

        return iconPane;
    }
}
