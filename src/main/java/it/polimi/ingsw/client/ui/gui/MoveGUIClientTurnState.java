package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractMoveClientTurnState;

public class MoveGUIClientTurnState extends AbstractMoveClientTurnState implements GUIClientTurnState{
    public MoveGUIClientTurnState(Client client, InGameGUIClientState clientState) {
        super(client);
    }

    @Override
    public void render() {

    }

    @Override
    public void notifyUiInteraction() {

    }
}
