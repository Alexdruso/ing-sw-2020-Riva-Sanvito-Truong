package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.GUI;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderBuilder;
import it.polimi.ingsw.utils.i18n.AvailableLocale;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.structures.BidirectionalHashMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the Settings screen
 */
public class SettingsController extends AbstractController {
    private static final Logger LOGGER = Logger.getLogger(SettingsController.class.getName());

    private final BidirectionalHashMap<AvailableLocale, ToggleButton> buttonMap = new BidirectionalHashMap<>();
    private ToggleGroup toggleGroup;

    @FXML
    HBox languageButtons;

    /**
     * Handles menu button on screen
     * @param event the mouse click event
     */
    @FXML
    void handleMenuButton(ActionEvent event){
        switchToMenu();
    }

    /**
     * This method sends the client to the menu
     */
    private void switchToMenu(){
        SceneLoaderBuilder sceneLoaderBuilder = new SceneLoaderBuilder("/fxml/MainMenu.fxml", client);
        sceneLoaderBuilder.setAttemptLoadFromSaved(true).build().executeSceneChange();
    }

    @Override
    public void onSceneShow(){
        buttonMap.getValueFromKey(AvailableLocale.fromLocale(I18n.getLocale())).setSelected(true);
    }

    /**
     * JavaFX initialization method
     */
    @FXML
    void initialize(){
        toggleGroup = new ToggleGroup();
        for(AvailableLocale locale: AvailableLocale.values()){
            ToggleButton button = new ToggleButton(locale.toString());
            button.setToggleGroup(toggleGroup);
            button.setGraphic(new ImageView(locale.icon));
            button.setPrefHeight(70);
            button.setPrefWidth(200);
            buttonMap.put(locale, button);
            languageButtons.getChildren().add(button);
        }
    }

    /**
     * This method saves the settings and sends the client to the menu
     * @param e the mouse click event
     */
    @FXML
    void saveSettings(ActionEvent e){
        I18n.setLocale(buttonMap.getKeyFromValue((ToggleButton)toggleGroup.getSelectedToggle()).locale);
        ((GUI)client.getUI()).clearSceneMap();
        switchToMenu();
    }

    @Override
    public void handleError(String message) {
        LOGGER.log(Level.SEVERE, message);
    }
}
