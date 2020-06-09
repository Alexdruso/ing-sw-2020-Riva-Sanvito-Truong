package it.polimi.ingsw.client.ui.gui;

public interface GUIClientTurnState {
    void selectCell(int x, int y);

    void skip();

    void cancel();

    void handleError();
}
