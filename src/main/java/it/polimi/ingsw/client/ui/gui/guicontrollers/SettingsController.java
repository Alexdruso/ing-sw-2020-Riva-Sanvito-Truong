package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.GUI;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderFactory;
import it.polimi.ingsw.utils.i18n.AvailableLocale;
import it.polimi.ingsw.utils.i18n.I18n;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SettingsController extends AbstractController {
    private static final Logger LOGGER = Logger.getLogger(SettingsController.class.getName());
    private static final String LANGUAGE_ENV_VAR_NAME = "LANGUAGE";

    @FXML
    ChoiceBox<AvailableLocale> languageChoiceBox;

    @FXML
    public void handleMenuButton(ActionEvent event){
        SceneLoaderFactory sceneLoaderFactory = new SceneLoaderFactory("/fxml/MainMenu.fxml", client);
        sceneLoaderFactory.setAttemptLoadFromSaved(true).build().executeSceneChange();
    }

    private void reloadScreen(){
        SceneLoaderFactory sceneLoaderFactory = new SceneLoaderFactory("/fxml/Settings.fxml", client);
        sceneLoaderFactory
                .setAttemptLoadFromSaved(true)
                .forceSceneChange(true)
                .setFadeOut(false)
                .setFadeIn(false)
                .build()
                .executeSceneChange();
    }

    @Override
    public void onSceneShow(){
        languageChoiceBox.setValue(AvailableLocale.fromLocale(I18n.getLocale()));
    }

    @FXML
    public void initialize(){
        languageChoiceBox.getItems().addAll(AvailableLocale.values());
    }

    @FXML
    public void saveSettings(ActionEvent e){
        I18n.setLocale(languageChoiceBox.getValue().locale);
        ((GUI)client.getUI()).clearSceneMap();
        reloadScreen();
    }

    @Override
    public void handleError(String message) {
        LOGGER.log(Level.SEVERE, message);
    }
}