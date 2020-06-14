package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.utils.networking.transmittables.ReducedTargetCells;

import java.util.ArrayList;
import java.util.List;

public class ReducedBoard {
    private final ReducedCell[][] cells;
    private static final int BOARD_SIZE = 5;

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


    /**
     * This method returns the ReducedCell instances targeted by target
     *
     * @param target the ReducedTargetCells instance with the cells that are targeted
     * @return a List of ReducedCell objects that were targeted
     */
    public List<ReducedCell> getTargets(ReducedTargetCells target){
        List<ReducedCell> targetedCells = new ArrayList<>();
        for(int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (target.getPosition(i, j)) {
                    targetedCells.add(cells[i][j]);
                }
            }
        }
        return targetedCells;
    }


}
