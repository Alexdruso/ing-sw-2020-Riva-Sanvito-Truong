package it.polimi.ingsw.client.ui.gui.guicontrollers.elements;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.guicontrollers.AbstractController;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoader;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderFactory;
import javafx.fxml.FXML;

public class MenuConfirmation extends AbstractController {
    private static Client client;

    @FXML
    void menu(){
        SceneLoader.applyBlurOut(1000);
        client.moveToState(ClientState.WELCOME_SCREEN);
    }

    @FXML
    void hide(){
        SceneLoader.applyBlurOut(1000);
    }

    public static void showMenuConfirmation(Client client){
        if(MenuConfirmation.client == null){
            MenuConfirmation.client = client;
        }
        SceneLoaderFactory sceneLoaderFactory = new SceneLoaderFactory("/fxml/MenuConfirmation.fxml", client);
        sceneLoaderFactory
                .setFadeIn(false)
                .setFadeOut(false)
                .setReplaceOldScene(false)
                .forceSceneChange(true)
                .build()
                .executeSceneChange();
    }

    @Override
    public void handleError(String message) {
        //No error to handle
    }
}
