package it.polimi.ingsw.client.ui.gui.guicontrollers.elements;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.guicontrollers.AbstractController;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoader;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderBuilder;
import javafx.fxml.FXML;

/**
 * This class represents an overlay used to ask the user for confirmation on its intention to return to the menu
 */
public class MenuConfirmation extends AbstractController {
    private static Client menuConfirmationClient;


    /**
     * This method sends the client to the menu
     */
    @FXML
    void menu(){
        SceneLoader.applyBlurOut(1000);
        menuConfirmationClient.moveToState(ClientState.WELCOME_SCREEN);
    }

    /**
     * This method remove the overlay
     */
    @FXML
    void hide(){
        SceneLoader.applyBlurOut(1000);
    }

    /**
     * This method is used to show the overlay
     * @param client the Client instance in which to show the overlay
     */
    public static void showMenuConfirmation(Client client){
        if(menuConfirmationClient == null){
            menuConfirmationClient = client;
        }
        SceneLoaderBuilder sceneLoaderBuilder = new SceneLoaderBuilder("/fxml/MenuConfirmation.fxml", client);
        sceneLoaderBuilder
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
