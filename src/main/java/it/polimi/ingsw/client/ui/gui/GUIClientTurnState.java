package it.polimi.ingsw.client.ui.gui;

/**
 * And empty interface for all TurnStates related to the GUI
 */
public interface GUIClientTurnState {
    /**
     * This method is used to select a cell on the board
     * @param x the x coordinate of the cell
     * @param y the y coordinate of the cell
     */
    void selectCell(int x, int y);

    /**
     * This method is used to skip the current turn state
     */
    void skip();

    /**
     * This method is used to cancel the last action, if so permitted
     */
    void cancel();

    /**
     * This method is used to notify the TurnState that an error has happened and defines how it should handle it
     */
    void handleError();
}
