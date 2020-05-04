package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractAskWorkerPositionClientTurnState;
import it.polimi.ingsw.client.clientstates.AbstractClientTurnState;

public class AskWorkerPositionGUIClientTurnState extends AbstractAskWorkerPositionClientTurnState implements GUIClientTurnState{
    public AskWorkerPositionGUIClientTurnState(Client client, InGameGUIClientState clientState) {
        super(client);
    }

    @Override
    public void render() {

    }

    @Override
    public void notifyUiInteraction() {

    }
}
