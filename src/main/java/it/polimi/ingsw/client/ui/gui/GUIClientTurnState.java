package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.reducedmodel.ReducedCell;

public interface GUIClientTurnState {
    void selectCell(int x, int y);

    void skip();

    void cancel();
}
