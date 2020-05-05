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
}
