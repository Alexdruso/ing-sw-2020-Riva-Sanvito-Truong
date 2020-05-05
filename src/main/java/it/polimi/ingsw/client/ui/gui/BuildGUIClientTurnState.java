package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractBuildClientTurnState;
import it.polimi.ingsw.client.clientstates.AbstractClientTurnState;
import it.polimi.ingsw.client.clientstates.ClientTurnState;

public class BuildGUIClientTurnState extends AbstractBuildClientTurnState implements GUIClientTurnState {
    public BuildGUIClientTurnState(Client client, InGameGUIClientState clientState) {
        super(client);
    }

    @Override
    public void render() {

    }

    @Override
    public void notifyUiInteraction() {

    }
}
