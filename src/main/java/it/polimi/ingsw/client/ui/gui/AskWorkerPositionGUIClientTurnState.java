package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractAskWorkerPositionClientTurnState;
import it.polimi.ingsw.client.reducedmodel.ReducedCell;

public class AskWorkerPositionGUIClientTurnState extends AbstractAskWorkerPositionClientTurnState implements GUIClientTurnState{
    private final InGameGUIClientState clientState;

    public AskWorkerPositionGUIClientTurnState(Client client, InGameGUIClientState clientState) {
        super(client);
        this.clientState = clientState;
    }

    @Override
    public void render() {

    }

    public void selectCell(ReducedCell cell) {
        targetCellX = cell.getX();
        targetCellY = cell.getY();
        clientState.notifyUiInteraction();
    }
}
