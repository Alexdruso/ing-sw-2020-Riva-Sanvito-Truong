package it.polimi.ingsw.model.board;

import it.polimi.ingsw.parsing.ConfigParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class represents the game board
 */

public class Board {
    /**
     * This groups all the tiles which make up the board
     */
    private final Cell[][] tiles;

    /**
     * This is the constant lenght of a side of the board
     */
    private static final int BOARD_SIZE = Integer.parseInt(ConfigParser.getInstance().getProperty("boardSize"));

    /**
     * This method creates a board with BOARD_SIZE*dimension cells
     */
    public Board() {
        this.tiles = new Cell[BOARD_SIZE][BOARD_SIZE];

        //initializes the cells with their coordinates
        for(int x = 0; x < BOARD_SIZE; x++){
            for(int y = 0; y < BOARD_SIZE; y++ ){
                tiles[x][y] = new Cell(x,y);
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
    public Cell getCell(int x, int y){
        return tiles[x][y];
    }

    /**
     * Gets a List of all the Cells in the board.
     *
     * @return the List of the Cells in the board
     */
    public List<Cell> getCellsList() {
        return Arrays.stream(tiles).flatMap(Arrays::stream).collect(Collectors.toList());
    }

    /**
     * This method returns the Cell instances targeted by target
     * @param target the TargetCells instance with the cells that are targeted
     * @return a List of Cell objects that were targeted
     */
    public List<Cell> getTargets(TargetCells target){
        List<Cell> targetedCells = new ArrayList<Cell>();
        for(int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (target.getPosition(i, j)) {
                    targetedCells.add(tiles[i][j]);
                }
            }
        }
        return targetedCells;
    }

    /**
     * From a given base cell and a direction, this method retrieves the cell starting from base
     * and moving by towards the given Direction
     * @param base the base Cell
     * @param direction the Direction
     * @return the resulting Cell, calculated starting from base and doing a translation towards Direction
     */
    public Optional<Cell> fromBaseCellAndDirection(Cell base, Direction direction){
        int newX = base.getX() + direction.dx;
        int newY = base.getY() + direction.dy;
        if(0 <= newX && newX < BOARD_SIZE && 0 <= newY && newY < BOARD_SIZE){
            return Optional.of(getCell(newX, newY));
        } else {
            return Optional.empty();
        }
    }
}
