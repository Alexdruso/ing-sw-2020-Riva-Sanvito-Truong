package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractClientState;

public abstract class AbstractController {
    Client client;
    AbstractClientState state;

    public void setClient(Client client){
        this.client = client;
    }

    public void setState(AbstractClientState state){
        this.state = state;
    }

    public abstract void handleError(String message);

    /**
     * Method to be overridden if there is the need to do some operations after the FXML is loaded but before
     * the scene is shown to the user. This gets called once, after FXML load
     */
    public void setupController(){ }

    /**
     * Method to be overridden if there is the need to do some operations everytime the scene is loaded,
     * both from FXML or, if the scene has already been loaded once, from the client cache
     */
    public void onSceneShow() { }
}
