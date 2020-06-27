package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractClientState;

/**
 * This abstract class defines all the methods that should be implemented by all JavaFX controllers
 */
public abstract class AbstractController {
    Client client;
    AbstractClientState state;

    /**
     * This method sets the client in the controller
     * @param client the Client instance
     */
    public void setClient(Client client){
        this.client = client;
    }

    /**
     * This method sets the ClientState in the controller
     * @param state the AbstractClientState instance
     */
    public void setState(AbstractClientState state){
        this.state = state;
    }

    /**
     * This method defines how the controller handles an error coming from the server
     * @param message the error message
     */
    public abstract void handleError(String message);

    /**
     * Method to be overridden if there is the need to do some operations after the FXML is loaded but before
     * the scene is shown to the user. This gets called once, after FXML load
     */
    public void setupController(){ }

    /**
     * Method to be overridden if there is the need to do some operations only the first time the scene is loaded,
     * both from FXML or, if the scene has already been loaded once, from the client cache
     */
    public void onSceneShow() { }
}
