package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.config.ConfigParser;

public class ReducedBoard {
    private final ReducedCell[][] cells;
    private static final int BOARD_SIZE = ConfigParser.getInstance().getIntProperty("boardSize");

    public ReducedBoard() {
        cells = new ReducedCell[BOARD_SIZE][BOARD_SIZE];

        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                cells[x][y] = new ReducedCell(x, y);
            }
        }
    }

    /**
     * This method returns the side length of the board
     * @return the side length of the board
     */
    public int getDimension(){
        return BOARD_SIZE;
    }

    /**
     * This method returns the cell in position (x,y)
     * @param x the position of the cell on the x axis
     * @param y the position of the cell on the y axis
     * @return the required cell
     */
    public ReducedCell getCell(int x, int y){
        return cells[x][y];
    }

}
